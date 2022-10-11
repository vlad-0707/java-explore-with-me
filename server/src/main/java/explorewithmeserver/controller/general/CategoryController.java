package explorewithmeserver.controller.general;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.model.category.CategoryDto;
import explorewithmeserver.service.general.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@Min(0) @RequestParam(required = false, defaultValue = "0") Integer from,
                                           @Min(1) @RequestParam(required = false, defaultValue = "10") Integer size) {
        return categoryService.getCategories(from, size);
    }


    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) throws NotFoundException {
        return categoryService.getCategoryById(catId);
    }
}
