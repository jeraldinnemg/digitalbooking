package com.digital.DigitaBooking.services;

import com.digital.DigitaBooking.models.dtos.CategoryDTO;
import com.digital.DigitaBooking.models.entities.Category;
import com.digital.DigitaBooking.models.entities.ImageCategory;

import java.util.Set;

public interface ICategoryService {

    Category saveCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategory(Integer id);

    void updateCategory(Integer id, CategoryDTO categoryDTO);

    void deleteCategory(Integer id);

    Set<CategoryDTO> getCategories();

}

