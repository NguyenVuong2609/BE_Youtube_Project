package com.example.demo.service.category;

import com.example.demo.model.Category;
import com.example.demo.service.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService extends IGenericService<Category> {
    Boolean existsByName(String name);
}
