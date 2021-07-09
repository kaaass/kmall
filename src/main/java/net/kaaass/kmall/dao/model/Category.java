package net.kaaass.kmall.dao.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Category {
    @Id
    private String id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "template_id")
    private String templateId;

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
     * @return category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    /**
     * @return parent_id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    /**
     * @return template_id
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * @param templateId
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }
}