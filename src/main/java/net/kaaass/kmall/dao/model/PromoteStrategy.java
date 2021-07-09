package net.kaaass.kmall.dao.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "promote_strategy")
public class PromoteStrategy {
    @Id
    private String id;

    private String clazz;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "promo_hint")
    private String promoHint;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @Column(name = "promo_name")
    private String promoName;

    @Column(name = "strategy_order")
    private Integer strategyOrder;

    @Column(name = "extra_param")
    private String extraParam;

    private String style;

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
     * @return clazz
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * @param clazz
     */
    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }

    /**
     * @return is_enabled
     */
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * @param isEnabled
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * @return promo_hint
     */
    public String getPromoHint() {
        return promoHint;
    }

    /**
     * @param promoHint
     */
    public void setPromoHint(String promoHint) {
        this.promoHint = promoHint == null ? null : promoHint.trim();
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
     * @return promo_name
     */
    public String getPromoName() {
        return promoName;
    }

    /**
     * @param promoName
     */
    public void setPromoName(String promoName) {
        this.promoName = promoName == null ? null : promoName.trim();
    }

    /**
     * @return strategy_order
     */
    public Integer getStrategyOrder() {
        return strategyOrder;
    }

    /**
     * @param strategyOrder
     */
    public void setStrategyOrder(Integer strategyOrder) {
        this.strategyOrder = strategyOrder;
    }

    /**
     * @return extra_param
     */
    public String getExtraParam() {
        return extraParam;
    }

    /**
     * @param extraParam
     */
    public void setExtraParam(String extraParam) {
        this.extraParam = extraParam == null ? null : extraParam.trim();
    }

    /**
     * @return style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style
     */
    public void setStyle(String style) {
        this.style = style == null ? null : style.trim();
    }
}