package net.kaaass.kmall.dao.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Cart {
    @Id
    private String id;

    @Column(name = "cart_count")
    private Integer cartCount;

    @Column(name = "create_time")
    private Date createTime;

    private String uid;

    @Column(name = "product_id")
    private String productId;

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
     * @return cart_count
     */
    public Integer getCartCount() {
        return cartCount;
    }

    /**
     * @param cartCount
     */
    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
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
     * @return uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * @return product_id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }
}