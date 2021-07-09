package net.kaaass.kmall.dao.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "table_order")
public class TableOrder {
    @Id
    private String id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "deliver_code")
    private String deliverCode;

    @Column(name = "deliver_time")
    private Date deliverTime;

    @Column(name = "mail_price")
    private Float mailPrice;

    @Column(name = "pay_time")
    private Date payTime;

    private Float price;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "order_type")
    private String orderType;

    private String uid;

    @Column(name = "address_id")
    private String addressId;

    @Column(name = "finish_time")
    private Date finishTime;

    @Column(name = "refund_time")
    private Date refundTime;

    private String reason;

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
     * @return deliver_code
     */
    public String getDeliverCode() {
        return deliverCode;
    }

    /**
     * @param deliverCode
     */
    public void setDeliverCode(String deliverCode) {
        this.deliverCode = deliverCode == null ? null : deliverCode.trim();
    }

    /**
     * @return deliver_time
     */
    public Date getDeliverTime() {
        return deliverTime;
    }

    /**
     * @param deliverTime
     */
    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
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
     * @return pay_time
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * @param payTime
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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
     * @return request_id
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @param requestId
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId == null ? null : requestId.trim();
    }

    /**
     * @return order_type
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param orderType
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
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
     * @return address_id
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * @param addressId
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId == null ? null : addressId.trim();
    }

    /**
     * @return finish_time
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * @param finishTime
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
     * @return refund_time
     */
    public Date getRefundTime() {
        return refundTime;
    }

    /**
     * @param refundTime
     */
    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    /**
     * @return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }
}