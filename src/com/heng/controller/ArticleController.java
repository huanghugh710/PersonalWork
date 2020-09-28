package com.heng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heng.domain.Article;
import com.heng.domain.ArticleType;
import com.heng.service.ArticleService;
import com.heng.service.ArticleTypeService;




@Controller
public class ArticleController {
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	ArticleTypeService articleTypeService;
	
	@RequestMapping(value="item.action")
	public String toItem(Integer id,Model model){
		//查询商品的一级分类
		List<ArticleType> firstArticleTypes = articleTypeService.getFirstArticleType();
		
		//查询商品
		Article atricle = articleService.getArticleById(id);
		
		model.addAttribute("article", atricle);

		//显示第一个级别的类型列表
		model.addAttribute("firstArticleTypes", firstArticleTypes);
		
		return "item";
		
	}

}
