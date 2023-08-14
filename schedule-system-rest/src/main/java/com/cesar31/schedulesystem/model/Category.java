package com.cesar31.schedulesystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @SequenceGenerator(name = "categoryIdGenerator", sequenceName = "SEQ_CATEGORY", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoryIdGenerator")
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "parent_category_id")
    private Long parentCategoryId;

    @Column(name = "internal_id")
    private Long internalId;

    @Column(name = "description")
    private String description;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public Long getInternalId() {
        return internalId;
    }

    public void setInternalId(Long internalId) {
        this.internalId = internalId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean is(Category other) {
        if (other == null)
            return false;

        return other.internalId.equals(internalId);
    }
}
