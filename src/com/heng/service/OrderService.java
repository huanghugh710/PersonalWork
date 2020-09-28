package com.heng.service;

import java.util.List;

import com.heng.domain.Order;
import com.heng.domain.OrderItem;

public interface OrderService {

	/**
	 * 保存订单和订单详情
	 * @param order
	 * @param orderItemList
	 */
	void saveOrderAndOrderItem(Order order, List<OrderItem> orderItemList);

	/**
	 * 查询订单和商品
	 * @param id
	 * @return
	 */
	List<Order> getOrderAndArticle(Integer id);

}
