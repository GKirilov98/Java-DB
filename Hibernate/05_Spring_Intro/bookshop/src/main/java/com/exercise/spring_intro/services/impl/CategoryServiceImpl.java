package com.exercise.spring_intro.services.impl;

import com.exercise.spring_intro.entities.Category;
import com.exercise.spring_intro.repositories.CategoryRepository;
import com.exercise.spring_intro.services.CategoryService;
import com.exercise.spring_intro.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORY_FILE_PATH = "src\\main\\resources\\files\\categories.txt";
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }


    @Override
    public void seedCategory() {
        if (this.categoryRepository.count() != 0){
            return;
        }

        String[] categories = this.fileUtil.fileContent(CATEGORY_FILE_PATH);
        for (String info : categories) {
            Category category = new Category();
            category.setName(info);

            this.categoryRepository.saveAndFlush(category);
        }
    }
}
