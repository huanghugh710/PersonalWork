package com.heng.service;

import com.heng.domain.Article;
import com.heng.domain.ArticleType;
import com.heng.vo.SearchVo;

import java.util.List;

/**
 * @author lfy
 * @Description:
 * @date 2020/9/1515:08
 */
public interface ArticleTypeService {

    /**
     * 模糊查询商品种类,查询一级分类
     * @return 商品集合
     */
    List<ArticleType> getFirstArticleType();

    /**
     * 二级查询,查询二级分类
     * @return
     */
    List<ArticleType> getSecondArticleType(SearchVo searchVo);

    /**
     * 根据条件查询商品
     * @param searchVo
     * @return
     */
    List<Article> getArticleList(SearchVo searchVo);
}
