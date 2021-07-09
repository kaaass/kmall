package net.kaaass.kmall.dao.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Plugin {
    @Id
    private String id;

    @Column(name = "enable_time")
    private Date enableTime;

    private String filename;

    private Boolean enable;

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
     * @return enable_time
     */
    public Date getEnableTime() {
        return enableTime;
    }

    /**
     * @param enableTime
     */
    public void setEnableTime(Date enableTime) {
        this.enableTime = enableTime;
    }

    /**
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    /**
     * @return enable
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * @param enable
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}