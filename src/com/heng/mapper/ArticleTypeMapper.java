package com.heng.mapper;

import com.heng.domain.ArticleType;
import com.heng.domain.ArticleTypeExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ArticleTypeMapper {
    int countByExample(ArticleTypeExample example);

    int deleteByExample(ArticleTypeExample example);

    int deleteByPrimaryKey(String code);

    int insert(ArticleType record);

    int insertSelective(ArticleType record);

    List<ArticleType> selectByExample(ArticleTypeExample example);

    ArticleType selectByPrimaryKey(String code);

    int updateByExampleSelective(@Param("record") ArticleType record, @Param("example") ArticleTypeExample example);

    int updateByExample(@Param("record") ArticleType record, @Param("example") ArticleTypeExample example);

    int updateByPrimaryKeySelective(ArticleType record);

    int updateByPrimaryKey(ArticleType record);
}