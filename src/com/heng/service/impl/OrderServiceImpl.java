package com.heng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heng.domain.Order;
import com.heng.domain.OrderItem;
import com.heng.mapper.OrderItemMapper;
import com.heng.mapper.OrderMapper;
import com.heng.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderItemMapper orderItemMapper;

	@Override
	public void saveOrderAndOrderItem(Order order, List<OrderItem> orderItemList) {
		//保存订单
		orderMapper.insertSelective(order);
		//返回订单id
		System.out.println(order.getId());
		
		for (OrderItem orderItem : orderItemList) {
			//封装订单id
			orderItem.setOrderId(order.getId());
			//保存订单详情
			orderItemMapper.insert(orderItem);
			
		}

	}

	@Override
	public List<Order> getOrderAndArticle(Integer id) {
		List<Order> orderList = orderMapper.getOrderAndArticle(id);
		return orderList;
	}

}
