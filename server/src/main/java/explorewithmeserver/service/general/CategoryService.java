package explorewithmeserver.service.general;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.model.category.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long catId) throws NotFoundException;
}
