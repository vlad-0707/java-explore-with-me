package explorewithmeserver.controller.admin;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.model.category.CategoryDto;
import explorewithmeserver.model.category.NewCategoryDto;
import explorewithmeserver.service.admin.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
@Validated
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @PatchMapping
    public CategoryDto update(@RequestBody CategoryDto category) throws NotFoundException {
        return adminCategoryService.update(category);
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody NewCategoryDto category) {
        return adminCategoryService.create(category);
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable Long catId) throws NotFoundException {
        adminCategoryService.delete(catId);
    }
}
