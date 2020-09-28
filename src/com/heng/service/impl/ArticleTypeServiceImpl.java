package com.heng.service.impl;

import com.github.pagehelper.PageHelper;
import com.heng.domain.Article;
import com.heng.domain.ArticleExample;
import com.heng.domain.ArticleType;
import com.heng.domain.ArticleTypeExample;
import com.heng.mapper.ArticleMapper;
import com.heng.mapper.ArticleTypeMapper;
import com.heng.service.ArticleTypeService;
import com.heng.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lfy
 * @Description:
 * @date 2020/9/1515:11
 */
@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {

    @Autowired
    private ArticleTypeMapper articleTypeMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<ArticleType> getFirstArticleType() {

        ArticleTypeExample articleTypeExample = new ArticleTypeExample();
        ArticleTypeExample.Criteria criteria = articleTypeExample.createCriteria();
        criteria.andCodeLike("00__");
        List<ArticleType> articleTypes = articleTypeMapper.selectByExample(articleTypeExample);
        return articleTypes;
    }

    @Override
    public List<ArticleType> getSecondArticleType(SearchVo searchVo) {

        ArticleTypeExample articleTypeExample = new ArticleTypeExample();
        ArticleTypeExample.Criteria criteria = articleTypeExample.createCriteria();

        if(searchVo.getFirstCode() != null && !"".equals(searchVo.getFirstCode())){
            //根据一级分类编码,查询二级分类商品
            criteria.andCodeLike(searchVo.getFirstCode() + "____");
        }else {
            //查询所有二级分类
            criteria.andCodeLike("00______");
        }

        List<ArticleType> secondArticleTypes = articleTypeMapper.selectByExample(articleTypeExample);

        return secondArticleTypes;
    }

    @Override
    public List<Article> getArticleList(SearchVo searchVo) {

        ArticleExample articleExample = new ArticleExample();
        ArticleExample.Criteria createCriteria = articleExample.createCriteria();

        if(searchVo.getSecondCode() != null && !"".equals(searchVo.getSecondCode())){
            createCriteria.andTypeCodeEqualTo(searchVo.getSecondCode());
        }else if (searchVo.getFirstCode() != null && !"".equals(searchVo.getFirstCode())){
            //根据一级分类编码,查询二级分类商品
            createCriteria.andTypeCodeLike(searchVo.getFirstCode() + "____");
        }

        if(searchVo.getKeyword()!=null&&!"".equals(searchVo.getKeyword())){
            createCriteria.andTitleLike("%"+searchVo.getKeyword()+"%");
        }

        //开启分页拦截
        PageHelper.startPage(searchVo.getPageIndex(),searchVo.getPageSize());
        List<Article> articleList = articleMapper.selectByExample(articleExample);

        return articleList;
    }
}
