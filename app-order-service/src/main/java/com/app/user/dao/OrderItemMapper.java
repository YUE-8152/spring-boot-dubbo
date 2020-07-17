package com.app.user.dao;

import com.app.api.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    List<OrderItem> selectAll();

    int updateByPrimaryKey(OrderItem record);
}