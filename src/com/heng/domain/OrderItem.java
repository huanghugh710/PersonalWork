package com.heng.domain;

public class OrderItem extends OrderItemKey {
    private Integer orderNum;
    
    
    //一个订单详情对应一件商品
    private Article article;
    
    

    public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}