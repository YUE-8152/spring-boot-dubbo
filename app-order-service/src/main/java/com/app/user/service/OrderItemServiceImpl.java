package com.app.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.app.api.model.OrderItem;
import com.app.api.service.order.OrderItemService;
import com.app.user.dao.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ProjectName: spring-boot-dubbo
 * @Package: com.app.order.service
 * @ClassName: OrderItemServiceImpl
 * @Author: YX
 * @Description: OrderItemService  接口实现类
 * @Date: 2020/7/17 16:05
 * @Version: 1.0
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItem> selectAll() {
        return orderItemMapper.selectAll();
    }
}
