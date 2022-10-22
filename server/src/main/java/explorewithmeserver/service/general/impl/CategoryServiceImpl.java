package explorewithmeserver.service.general.impl;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.mapper.CategoryMapper;
import explorewithmeserver.model.category.Category;
import explorewithmeserver.model.category.CategoryDto;
import explorewithmeserver.repository.CategoryRepository;
import explorewithmeserver.service.general.CategoryService;
import explorewithmeserver.valid.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final Validator validator;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        List<Category> categories = repository
                .findAll(PageRequest.of(from, size))
                .getContent();
        return categories.stream().map(mapper::mapToCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long catId) throws NotFoundException {
        Category category = validator.validCategory(catId);
        return mapper.mapToCategoryDto(category);
    }


}
