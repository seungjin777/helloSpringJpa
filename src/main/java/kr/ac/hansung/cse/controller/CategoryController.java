package kr.ac.hansung.cse.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.cse.exception.DuplicateCategoryException;
import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.model.CategoryForm;
import kr.ac.hansung.cse.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor // 생성자 코드 자동 생성
public class CategoryController {

    private final CategoryService categoryService;

    //GET /categories - 카테고리 목록 조회
    @GetMapping
    public String listCategories(Model model){
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories); //타임리프가 model 꺼내서 랜더링 할 수 있게
        return "categoryList";
    }


    //GET /categories/create - get 카테고리 등록 폼 표시
    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("categoryForm", new CategoryForm());
        return "categoryForm";
    }


    //POST /categories/create - post 카테고리 등록 처리
    @PostMapping("/create")
    public String createCategory(
            @Valid @ModelAttribute CategoryForm categoryForm, // form 객체에 바인딩
            BindingResult bindingResult,                    // 검증 결과를 담아 예외 프론트에서 처리할 수 있게
            RedirectAttributes redirectAttributes ){

        if (bindingResult.hasErrors()) return "categoryForm"; // 오류나면 다시 폼페이지로 가라

        try{
            categoryService.createCategory(categoryForm.getName()); // 폼에 입력된 이름으로 카테고리 중복검사 후 생성
            redirectAttributes.addAttribute("suceessMessage", "등록 완료");
        } catch (DuplicateCategoryException e){
            // 중복 예외 처리    -- rejectValue(필드명, 오류코드, 오류메시지)
            bindingResult.rejectValue("name", "duplicate", e.getMessage());
            return "categoryForm";
        }

        return "redirect:/categories"; // 정상 등록시 리다이렉트
    }


    //POST /categories/{id}/delete - 카테고리 삭제 처리
    @PostMapping("/{id}/delete")
    public String deleteCategory(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes){
        try{
            categoryService.deleteCategory(id); // 삭제 로직 호출
            redirectAttributes.addFlashAttribute("successMessage", "삭제 완료");
        }catch(IllegalStateException e){
            // 연결된 상품이 있는 경우 예외 처리
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/categories";
    }
}
