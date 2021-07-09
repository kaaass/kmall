package net.kaaass.kmall.dao.model;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "product_storage")
public class ProductStorage {
    @Id
    private String id;

    private Integer rest;

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
     * @return rest
     */
    public Integer getRest() {
        return rest;
    }

    /**
     * @param rest
     */
    public void setRest(Integer rest) {
        this.rest = rest;
    }
}