package net.kaaass.kmall.dao.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Product {
    @Id
    private String id;

    @Column(name = "buy_limit")
    private Integer buyLimit;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @Column(name = "mail_price")
    private Float mailPrice;

    @Column(name = "product_name")
    private String productName;

    private Float price;

    @Column(name = "category_id")
    private String categoryId;

    private String thumbnail;

    @Column(name = "storage_id")
    private String storageId;

    @Column(name = "start_sell_time")
    private Date startSellTime;

    @Column(name = "index_order")
    private Integer indexOrder;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return buy_limit
     */
    public Integer getBuyLimit() {
        return buyLimit;
    }

    /**
     * @param buyLimit
     */
    public void setBuyLimit(Integer buyLimit) {
        this.buyLimit = buyLimit;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return last_update_time
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * @return mail_price
     */
    public Float getMailPrice() {
        return mailPrice;
    }

    /**
     * @param mailPrice
     */
    public void setMailPrice(Float mailPrice) {
        this.mailPrice = mailPrice;
    }

    /**
     * @return product_name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * @return price
     */
    public Float getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * @return category_id
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    /**
     * @return thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @param thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
    }

    /**
     * @return storage_id
     */
    public String getStorageId() {
        return storageId;
    }

    /**
     * @param storageId
     */
    public void setStorageId(String storageId) {
        this.storageId = storageId == null ? null : storageId.trim();
    }

    /**
     * @return start_sell_time
     */
    public Date getStartSellTime() {
        return startSellTime;
    }

    /**
     * @param startSellTime
     */
    public void setStartSellTime(Date startSellTime) {
        this.startSellTime = startSellTime;
    }

    /**
     * @return index_order
     */
    public Integer getIndexOrder() {
        return indexOrder;
    }

    /**
     * @param indexOrder
     */
    public void setIndexOrder(Integer indexOrder) {
        this.indexOrder = indexOrder;
    }
}