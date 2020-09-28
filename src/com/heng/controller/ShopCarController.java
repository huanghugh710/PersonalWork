package com.heng.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.heng.domain.Article;
import com.heng.service.ArticleService;

/**
 * 使用session实现购物车
 * @author Lenovo
 *
 */
@Controller
public class ShopCarController {
	@Autowired
	ArticleService articleService;
	
	/**
	 * 创建购物车
	 * @param model
	 * @param article
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/buy.action")
	public String toShopCar(Model model,Article article,HttpSession session){
		System.out.println("用户购买的数量："+article.getBuyNum()+" 用户购买的物品id："+article.getId());
		
		//第一次点击加入购物车时，session并没有保存物品信息，此时购物车为空，我们需要创建一个购物车，将物品信息保存到购物车中，再将购物车保存至session中
		//用户第二次点击加入购物车时，此时购物车不为空，我们不需要创建购物车，需要的是更新购物车中的数据
		
		//拿到购物车
		Map<Integer, Integer> shopCarMap = (Map<Integer, Integer>) session.getAttribute("shop_car");
		if(shopCarMap==null){
			//创建购物车
			shopCarMap = new HashMap<>();
			//将物品id作为key，物品数量作为value保存至Map集合中
			shopCarMap.put(article.getId(),article.getBuyNum());
		}else{
			//更新购物车中的数据
			//判断用户购买的物品是否已经存在与购物车中，假如已经存在，则更新物品数量，不存在，则添加至购物车
			//shopCarMap中的key存放的是物品id，value存放的是物品数量
			if(shopCarMap.containsKey(article.getId())){
				//已经存在，更新物品数量
				//key：存放的是物品id  value:存在的是物品数量   buyNum：用户最新购买的数量；shopCarMap.get(article.getId())作用是根据物品id从购物车中取出购买的数量
				//先获取购物车中原来的商品数量  shopCarMap.get(article.getId())
				shopCarMap.put(article.getId(), shopCarMap.get(article.getId()) + article.getBuyNum());
			}else{
				//不存在，添加至购物车
				shopCarMap.put(article.getId(),article.getBuyNum());
			}
			
			
			
		}
		
		
		
		session.setAttribute("shop_car",shopCarMap );
		
		
		return "redirect:showCar.action";
	}
	/**
	 * 从购物车获取商品
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/showCar.action")
	public String showCar(Model model,HttpSession session){
		//获取购物车
		Map<Integer, Integer> shopCar = (Map<Integer, Integer>) session.getAttribute("shop_car");
		
		List<Article> articleList = new ArrayList<>();
		//数量总计
		Integer totalNum = 0;
		//物品折扣价
		Double totalPrice = 0.0;
		
		if (shopCar!=null) {
			for (Map.Entry<Integer, Integer> map : shopCar.entrySet()) {
				//拿到物品id
				Integer id = map.getKey();
				//拿到物品数量
				Integer num = map.getValue();

				totalNum = totalNum + num;

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
		//将购物车中物品总数量保存至购物车中
		model.addAttribute("totalNum", totalNum);
		
		return "shopcar";
		
	}
	
	/**
	 * 更新购物车
	 * @param model
	 * @param article
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/updateCar.action")
	public String updateCar(Model model,Article article,HttpSession session){
		if(article.getId()!=null){
			//拿到购物车
			Map<Integer, Integer> shopCarMap = (Map<Integer, Integer>) session.getAttribute("shop_car");
			//判断购物车中是否包含该物品id
			if(shopCarMap!=null&&shopCarMap.containsKey(article.getId())){
				shopCarMap.put(article.getId(),article.getBuyNum());
			}
			model.addAttribute("shop_car", shopCarMap);
		}
		
		return "redirect:showCar.action";
		
	}
	/**
	 * 删除购物车
	 * @param model
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/deleteCar.action")
	public String deleteCar(Model model,Integer id,HttpSession session){
		if(id!=null){
			//拿到购物车
			Map<Integer, Integer> shopCarMap = (Map<Integer, Integer>) session.getAttribute("shop_car");
			//判断购物车中是否包含该物品id
			if(shopCarMap!=null&&shopCarMap.containsKey(id)){
				shopCarMap.remove(id);
			}
			model.addAttribute("shop_car", shopCarMap);
		}
		
		return "redirect:showCar.action";
		
	}

}
