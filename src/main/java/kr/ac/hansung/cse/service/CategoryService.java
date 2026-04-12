package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.exception.DuplicateCategoryException;
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
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 중복 검사 후 저장
    @Transactional // 오버라이드 -> 쓰기 허용 (디폴트 false)
    public Category createCategory(String name){
        // 중복검사
        categoryRepository.findByName(name)
                .ifPresent(c -> {throw new DuplicateCategoryException(name); });
                // ifPresent() 메소드 = 값을 가지고 있는지 확인 후 예외처리
        return categoryRepository.save(new Category(name)); //유효성 검증 통과시 저장
    }

//    @Transactional
//    public void deleteCategory(Long id){
//
//    }

}
