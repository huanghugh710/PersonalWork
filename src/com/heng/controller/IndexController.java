package com.heng.controller;

import com.github.pagehelper.PageInfo;
import com.heng.domain.Article;
import com.heng.domain.ArticleType;
import com.heng.service.ArticleTypeService;
import com.heng.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 首页
 * @author Lenovo
 *
 */
@Controller
public class IndexController {

	@Autowired
	private ArticleTypeService articleTypeService;
	
	@RequestMapping(value="/toIndex")
	public String toIndex(Model model, SearchVo searchVo){

		//查询一级分类商品总称
		List<ArticleType> firstArticleTypes = articleTypeService.getFirstArticleType();
		//显示一级分类列表
		model.addAttribute("firstArticleTypes",firstArticleTypes);

		//查询二级分类商品
		List<ArticleType> secondArticleTypes = articleTypeService.getSecondArticleType(searchVo);
		//显示二级分类列表
		model.addAttribute("secondarticleTypes",secondArticleTypes);

		//查询商品
		List<Article> articleList = articleTypeService.getArticleList(searchVo);
		//显示商品列表
		model.addAttribute("articles", articleList);
		//封装查询结果
		PageInfo pageInfo = new PageInfo(articleList);
		searchVo.setPageIndex(pageInfo.getPageNum());
		searchVo.setPageSize(pageInfo.getPageSize());
		searchVo.setRecordCount(pageInfo.getTotal());
		model.addAttribute("searchVo",searchVo);

		return "list";
	}

}
