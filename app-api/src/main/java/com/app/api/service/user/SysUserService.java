package com.app.api.service.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.app.api.model.SysUser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: app-api
 * @Package: com.app.api.service
 * @ClassName: SysUserService
 * @Author: YX
 * @Description: SysUserService 接口
 * @Date: 2020/7/13 14:27
 * @Version: 1.0
 */
public interface SysUserService {

    List<SysUser> selectAll();
}
