package explorewithmeserver.service.admin;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.model.category.CategoryDto;
import explorewithmeserver.model.category.NewCategoryDto;

public interface AdminCategoryService {

    CategoryDto update(CategoryDto categoryDto) throws NotFoundException;

    CategoryDto create(NewCategoryDto category);

    void delete(Long catId) throws NotFoundException;
}
