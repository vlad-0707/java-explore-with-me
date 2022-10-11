package explorewithmeserver.controller.admin;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.model.category.CategoryDto;
import explorewithmeserver.model.category.NewCategoryDto;
import explorewithmeserver.service.admin.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/categories")
@Validated
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @PatchMapping
    public CategoryDto update(@Valid @RequestBody CategoryDto category) throws NotFoundException {
        return adminCategoryService.update(category);
    }

    @PostMapping
    public CategoryDto addCategory(@Valid @RequestBody NewCategoryDto category) {
        return adminCategoryService.create(category);
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable Long catId) throws NotFoundException {
        adminCategoryService.delete(catId);
    }
}
