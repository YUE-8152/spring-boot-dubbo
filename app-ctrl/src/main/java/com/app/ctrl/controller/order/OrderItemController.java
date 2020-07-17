package com.app.ctrl.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.app.api.service.order.OrderItemService;
import com.app.common.core.Result;
import com.app.common.core.ResultGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: spring-boot-dubbo
 * @Package: com.app.ctrl.controller.order
 * @ClassName: OrderItemController
 * @Author: YX
 * @Description: OrderItem  接口
 * @Date: 2020/7/17 16:09
 * @Version: 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderItemController {
    @Reference
    private OrderItemService orderItemService;

    @GetMapping("/selectAll")
    public Result selectAll() {
        return ResultGenerator.success(orderItemService.selectAll());
    }
}
