package com.heng.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heng.domain.Article;
import com.heng.domain.Order;
import com.heng.domain.OrderItem;
import com.heng.domain.User;
import com.heng.service.ArticleService;
import com.heng.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	ArticleService articleService;
	@Autowired
	OrderService orderService;
	
	/**
	 * 确认订单
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/order.action")
	public String order(Model model,HttpSession session){
		
		//获取购物车
		Map<Integer, Integer> shopCar = (Map<Integer, Integer>) session.getAttribute("shop_car");
		
		List<Article> articleList = new ArrayList<>();
		
		//物品折扣价
		Double totalPrice = 0.0;
		
		if (shopCar!=null) {
			for (Map.Entry<Integer, Integer> map : shopCar.entrySet()) {
				//拿到物品id
				Integer id = map.getKey();
				//拿到物品数量
				Integer num = map.getValue();

				

				//根据id查询商品
				Article article = articleService.getArticleById(id);
				//获取物品折扣价(总价)
				Double discountPrice = article.getDiscountPrice();
				//totalPrice = totalPrice + (discountPrice * num);

				//将用户购买的物品数量存放在article对象中
				article.setBuyNum(num);

				totalPrice = totalPrice + article.getSmallTotal();

				articleList.add(article);

			} 
		}
		//将购物车中物品总金额保存至购物车中
		DecimalFormat df = new DecimalFormat("0.00");
		model.addAttribute("totalPrice", df.format(totalPrice));
		
		//将购物中所有的物品信息保存至request对象中
		model.addAttribute("articleList", articleList);
		
		
		
		return "order";
		
	}
	
	/**
	 * 保存订单信息
	 * @param model
	 * @param order
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/saveOrder.action")
	public String saveOrder(Model model,Order order,HttpSession session){
		System.out.println(order.getAmount());
		User user = (User) session.getAttribute("session_user");
		if (user!=null) {
			//封装订单表的其他的内容
			//用户id
			
			order.setUserId(user.getId());
			//订单编号 PO_5_20200921091649
			StringBuffer orderCode = new StringBuffer();
			orderCode.append("PO_");
			orderCode.append(user.getId()+"_");
			Date date = new Date();
			//封装日期
			order.setCreateDate(date);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateStr = simpleDateFormat.format(date);
			orderCode.append(dateStr);
			System.out.println(orderCode);
			
			//封装订单编号
			order.setOrderCode(orderCode.toString());
			
			
			//order_item表
			

			//获取购物车
			Map<Integer, Integer> shopCar = (Map<Integer, Integer>) session.getAttribute("shop_car");
			
			List<OrderItem> orderItemList = new ArrayList<>();
			
			if (shopCar!=null) {
				for (Map.Entry<Integer, Integer> map : shopCar.entrySet()) {
					//拿到物品id
					Integer id = map.getKey();
					//拿到物品数量
					Integer num = map.getValue();
					
					OrderItem orderItem = new OrderItem();

					orderItem.setArticleId(id);
					orderItem.setOrderNum(num);
					
					orderItemList.add(orderItem);


				} 
			}
			
			//调用serviceAPI
			orderService.saveOrderAndOrderItem(order,orderItemList);
			//清空购物车中的数据
			session.removeAttribute("shop_car");
		}
		
		
		
		return "redirect:orderList.action";
		
	}
	
	/**
	 * 查询订单信息
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/orderList.action")
	public String orderList(Model model,HttpSession session){
		User user = (User) session.getAttribute("session_user");
		if(user!=null){
			List<Order> orderList = orderService.getOrderAndArticle(user.getId());
			model.addAttribute("orderList", orderList);
			model.addAttribute("size", orderList.size());
		}
		
		return "orderList";
		
		
	}
		

}
