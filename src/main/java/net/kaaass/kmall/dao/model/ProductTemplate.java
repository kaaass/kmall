package net.kaaass.kmall.dao.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "product_template")
public class ProductTemplate {
    @Id
    private String id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "template_schema")
    private String templateSchema;

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
     * @return template_name
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    /**
     * @return template_schema
     */
    public String getTemplateSchema() {
        return templateSchema;
    }

    /**
     * @param templateSchema
     */
    public void setTemplateSchema(String templateSchema) {
        this.templateSchema = templateSchema == null ? null : templateSchema.trim();
    }
}