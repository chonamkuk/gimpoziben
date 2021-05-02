package kr.co.kimpoziben.service;

import kr.co.kimpoziben.domain.entity.Category;
import kr.co.kimpoziben.domain.entity.Size;
import kr.co.kimpoziben.domain.repository.CategoryRepository;
import kr.co.kimpoziben.dto.CategoryDto;
import kr.co.kimpoziben.dto.SizeDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> getList() throws  Exception {
        List<CategoryDto> categoryList = new ArrayList<>();

        for(Category category : categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "nmCategory"))) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setSeqCategory(category.getSeqCategory());
            categoryDto.setNmCategory(category.getNmCategory());
            categoryList.add(categoryDto);
        }

        return categoryList;
    }

    public List<CategoryDto> getChildList(Long seqUpper) throws Exception {
        List<CategoryDto> categoryList = new ArrayList<>();

        for(Category category : categoryRepository.findAllBySeqUpper(seqUpper, Sort.by(Sort.Direction.ASC, "nmCategory"))) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setSeqCategory(category.getSeqCategory());
            categoryDto.setNmCategory(category.getNmCategory());
            categoryList.add(categoryDto);
        }

        return categoryList;
    }

    public List<CategoryDto> getParentList() throws Exception {
        List<CategoryDto> categoryList = new ArrayList<>();

        for(Category category : categoryRepository.findAllBySeqUpperIsNull(Sort.by(Sort.Direction.ASC, "nmCategory"))) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setSeqCategory(category.getSeqCategory());
            categoryDto.setNmCategory(category.getNmCategory());
            categoryList.add(categoryDto);
        }

        return categoryList;
    }
}
