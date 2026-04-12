package kr.ac.hansung.cse.controller;

import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor // 생성자 코드 자동 생성
public class CategoryController {

    private final CategoryService categoryService;

    //GET /categories - 카테고리 목록 조회
    @GetMapping
    public String listCategories(Model model){
        List<Category> categories = categoryService.getAllCategorys();
        model.addAttribute("categories", categories); //타임리프가 model 꺼내서 랜더링 할 수 있게
        return "categoryList";
    }
}
