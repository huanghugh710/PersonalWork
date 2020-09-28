package com.heng.domain;

import java.text.DecimalFormat;
import java.util.Date;

public class Article {
    private Integer id;

    private String articleName;

    private String title;

    private String supplier;

    private Double price;

    private String locality;

    private Date putawayDate;

    private Integer storage;

    private String image;

    private String description;

    private String typeCode;

    private Date createDate;

    /**
     * 数量
     */
    private Integer buyNum;

    //获取折扣价
    public Double getDiscountPrice() {

        //格式化工具
        DecimalFormat df = new DecimalFormat("0.00");
        //将字符窜转换成double类型
        return Double.valueOf(df.format(0.1 * this.price));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName == null ? null : articleName.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality == null ? null : locality.trim();
    }

    public Date getPutawayDate() {
        return putawayDate;
    }

    public void setPutawayDate(Date putawayDate) {
        this.putawayDate = putawayDate;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    //获取物品小计价格（物品价格*购买数量）
    public Double getSmallTotal() {

        //格式化工具
        DecimalFormat df = new DecimalFormat("0.00");
        //将字符窜转换成double类型
        return Double.valueOf(df.format(this.buyNum*(0.76 * this.price)));
    }
}