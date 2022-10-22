package explorewithmeserver.service.admin.impl;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.mapper.CategoryMapper;
import explorewithmeserver.model.category.Category;
import explorewithmeserver.model.category.CategoryDto;
import explorewithmeserver.model.category.NewCategoryDto;
import explorewithmeserver.repository.CategoryRepository;
import explorewithmeserver.service.admin.AdminCategoryService;
import explorewithmeserver.valid.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final Validator validator;

    @Override
    public CategoryDto update(CategoryDto categoryDto) throws NotFoundException {
        Category category = validator.validCategory(categoryDto.getId());
        repository.save(mapper.mapToCategory(categoryDto));
        return mapper.mapToCategoryDto(category);
    }

    @Override
    public CategoryDto create(NewCategoryDto categoryDto) {
        Category category = repository.save(mapper.mapToCategory(categoryDto));
        return mapper.mapToCategoryDto(category);
    }

    @Override
    public void delete(Long catId) throws NotFoundException {
        validator.validCategory(catId);
        repository.deleteById(catId);
    }
}
