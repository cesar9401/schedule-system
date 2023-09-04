package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.model.Category;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Repository(forEntity = Category.class)
public abstract class CategoryRepository extends AbstractEntityRepository<Category, Long> {

    abstract Category findByInternalId(Long internalId);

    abstract List<Category> findByParentCategoryId(Long parentCategoryId);

    public List<Category> findByParentInternalId(Long parentInternalId) {
        var parent = this.findByInternalId(parentInternalId);
        if (parent == null) return new ArrayList<>();

        return this.findByParentCategoryId(parent.getCategoryId());
    }
}
