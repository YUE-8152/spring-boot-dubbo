package com.app.api.service.order;

import com.app.api.model.OrderItem;

import java.util.List;

/**
 * @ProjectName: spring-boot-dubbo
 * @Package: com.app.api.service.order
 * @ClassName: OrderItemService
 * @Author: YX
 * @Description: OrderItem 接口
 * @Date: 2020/7/17 16:02
 * @Version: 1.0
 */
public interface OrderItemService {
    List<OrderItem> selectAll();
}
