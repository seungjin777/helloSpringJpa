package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.model.Category;
import kr.ac.hansung.cse.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //service layer 스프링 빈으로 등록
@Transactional(readOnly = true) // 클래스 기본값: 읽기 전용 트랜잭션
@RequiredArgsConstructor
public class CategoryService {

    // lombok으로 생성자 생략함 (productService는 롬복안씀 why?)
    private final CategoryRepository categoryRepository;

    // 모든 카테고리 종류 조회
    public List<Category> getAllCategorys() {
        return categoryRepository.findAll();
    }

}
