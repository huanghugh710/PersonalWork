package com.heng.mapper;

import com.heng.domain.ArticleCar;
import com.heng.domain.ArticleCarExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ArticleCarMapper {
    int countByExample(ArticleCarExample example);

    int deleteByExample(ArticleCarExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleCar record);

    int insertSelective(ArticleCar record);

    List<ArticleCar> selectByExample(ArticleCarExample example);

    ArticleCar selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleCar record, @Param("example") ArticleCarExample example);

    int updateByExample(@Param("record") ArticleCar record, @Param("example") ArticleCarExample example);

    int updateByPrimaryKeySelective(ArticleCar record);

    int updateByPrimaryKey(ArticleCar record);
}