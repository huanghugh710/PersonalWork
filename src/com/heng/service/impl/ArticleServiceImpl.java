package com.heng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heng.domain.Article;
import com.heng.domain.ArticleExample;
import com.heng.mapper.ArticleMapper;
import com.heng.service.ArticleService;
import com.heng.vo.SearchVo;
import com.github.pagehelper.PageHelper;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleMapper articleMapper;
	
	@Override
	public List<Article> getArticleList(SearchVo searchVo) {
		
		ArticleExample articleExample = new ArticleExample();
		ArticleExample.Criteria createCriteria = articleExample.createCriteria();
		
		if(searchVo.getSecondCode()!=null&&!"".equals(searchVo.getSecondCode())){
			createCriteria.andTypeCodeEqualTo(searchVo.getFirstCode());
		}else if(searchVo.getFirstCode()!=null&&!"".equals(searchVo.getFirstCode())){
			createCriteria.andTypeCodeLike(searchVo.getFirstCode()+"____");
		}
		if(searchVo.getKeyword()!=null&&!"".equals(searchVo.getKeyword())){
			createCriteria.andTitleLike("%"+searchVo.getKeyword()+"%");
		}
		
		//开启分页拦截
		PageHelper.startPage(searchVo.getPageIndex(), searchVo.getPageSize());
		List<Article> articleList = articleMapper.selectByExample(articleExample);
		
		return articleList;
	}

	
	@Override
	public Article getArticleById(Integer id) {
		Article atricle = articleMapper.selectByPrimaryKey(id);
		return atricle;
	}

}
