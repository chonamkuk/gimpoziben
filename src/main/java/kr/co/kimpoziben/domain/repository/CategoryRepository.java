package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.Category;
import kr.co.kimpoziben.domain.repository.dsl.CategoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
}
