package net.kaaass.kmall.dao.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_metadata")
public class UserMetadata {
    @Id
    private String id;

    @Column(name = "meta_key")
    private String metaKey;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    private String uid;

    @Column(name = "meta_value")
    private String metaValue;

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
     * @return meta_key
     */
    public String getMetaKey() {
        return metaKey;
    }

    /**
     * @param metaKey
     */
    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey == null ? null : metaKey.trim();
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
     * @return meta_value
     */
    public String getMetaValue() {
        return metaValue;
    }

    /**
     * @param metaValue
     */
    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue == null ? null : metaValue.trim();
    }
}