package com.heng.service;

import java.util.List;

import com.heng.domain.Article;
import com.heng.domain.Order;
import com.heng.domain.OrderItem;
import com.heng.vo.SearchVo;

public interface ArticleService {
	/**
	 * 根据id查询商品
	 * @param id
	 * @return
	 */
	public Article getArticleById(Integer id);
	/**
	 * 根据条件查询商品
	 * @param searchVo
	 * @return
	 */
	List<Article> getArticleList(SearchVo searchVo);
	
}
