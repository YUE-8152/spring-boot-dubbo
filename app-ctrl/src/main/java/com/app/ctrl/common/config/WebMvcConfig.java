package com.app.ctrl.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.app.common.core.Result;
import com.app.common.core.ResultCode;
import com.app.common.core.ServiceException;
import com.app.ctrl.common.interceptor.TokenInterceptor;
import com.app.ctrl.common.jwt.LoginUserArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Spring MVC 配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);
    @Value("${spring.profiles.active}")
    private String env;   //当前激活的配置文件

    @Autowired
    private LoginUserArgumentResolver loginUserArgumentResolver;

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }


    //WebMvcConfig extends WebMvcConfigurationSupport会使用默认MessageConverter，
    //导致配置文件中的格式化日期配置不生效：spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
    //使得Controller层返回的时间为时间戳，而不是数据库库对应的格式
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);//保留空的字段
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8));
        converters.add(converter);
    }


    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
                Result result = new Result();
                if (e instanceof ServiceException) {//业务失败的异常，如“账号或密码错误”
                    result.setStatus(ResultCode.FAIL).setMsg(e.getMessage());
                    logger.error(e.getMessage());
                } else if (e instanceof NoHandlerFoundException) {
                    logger.error("---------------接口{}不存在", request.getRequestURI());
                    result.setStatus(ResultCode.NOT_FOUND).setMsg("接口 [" + request.getRequestURI() + "] 不存在");
                } else if (e instanceof HttpMessageNotReadableException) {
                    logger.error("参数错误---------------：" + e.getMessage());
                    result.setStatus(ResultCode.PARAMETERS_IS_REQUIRE).setMsg("接口 [" + request.getRequestURI() + "] 参数错误");
                } else if (e instanceof MethodArgumentNotValidException) {
                    logger.error("参数错误---------------：" + e.getMessage());
                    result.setStatus(ResultCode.PARAMETERS_NOT_VALID).setMsg("接口 [" + request.getRequestURI() + "] 参数错误");
                } else if (e instanceof ServletException) {
                    result.setStatus(ResultCode.FAIL).setMsg(e.getMessage()).setIsSuccess(false);
                } else {
                    logger.error("---------------接口{}内部错误", request.getRequestURI());
                    result.setStatus(ResultCode.INTERNAL_SERVER_ERROR).setMsg("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
                    String message;
                    if (handler instanceof HandlerMethod) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                                request.getRequestURI(),
                                handlerMethod.getBean().getClass().getName(),
                                handlerMethod.getMethod().getName(),
                                e.getMessage());
                    } else {
                        message = e.getMessage();
                    }
                    logger.error(message, e);
                }
                responseResult(response, result);
                return new ModelAndView();
            }
        });
    }

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600 * 24);
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //接口签名认证拦截器，该签名认证比较简单，实际项目中可以使用Json Web Token或其他更好的方式替代。
        if (!"dev".equals(env)) { //开发环境忽略签名认证
            registry.addInterceptor(tokenInterceptor)
                    .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**",
                            "/swagger-ui.html/**", "/druid/*");//排除路径
        }
    }

    /**
     * 继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。 需要重新指定静态资源
     * 否则无法访问相关资源文件；如：http://localhost:8081/swagger-ui.html
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
}
