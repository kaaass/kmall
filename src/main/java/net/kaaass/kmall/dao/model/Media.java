package net.kaaass.kmall.dao.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Media {
    @Id
    private String id;

    private String type;

    @Column(name = "upload_time")
    private Date uploadTime;

    private String url;

    @Column(name = "uploader_uid")
    private String uploaderUid;

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
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return upload_time
     */
    public Date getUploadTime() {
        return uploadTime;
    }

    /**
     * @param uploadTime
     */
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return uploader_uid
     */
    public String getUploaderUid() {
        return uploaderUid;
    }

    /**
     * @param uploaderUid
     */
    public void setUploaderUid(String uploaderUid) {
        this.uploaderUid = uploaderUid == null ? null : uploaderUid.trim();
    }
}