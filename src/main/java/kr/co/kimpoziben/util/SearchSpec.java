package kr.co.kimpoziben.util;

import kr.co.kimpoziben.dto.SearchDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SearchSpec {
    public static Specification<?> searchLike(SearchDto searchDto) {
        return (Specification<?>) ((root, query, builder) ->
                builder.like(root.get(searchDto.getSearchType()), "%" + searchDto.getSearchKeyword() + "%")
        );
    }


    public static Specification<?> searchLike2(SearchDto searchDto,String delYn) {
        return (Specification<?>) ((root, query, builder) ->
                builder.equal(root.get("delYn"),delYn)
        );
    }

    public static Specification<?> searchLike3(SearchDto searchDto) {
        return (Specification<?>) ((root, query, builder) -> {
            List<Predicate> predicates = getPredicates(root, searchDto, builder);
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public static Specification<?> searchLike4(HashMap searchCondition) {
        return (Specification<?>) ((root, query, builder) -> {
            List<Predicate> predicates = getPredicates(root, searchCondition, builder);
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }


    /**
     * 게시판 검색조건 생성하는 메소드
     * searchType(검색구분), searchKeyword(검색어), ynDel(게시글 삭제여부) 조건만 받는다
     * @param root
     * @param searchDto
     * @param builder
     * @return
     */
    public static List<Predicate> getPredicates(Root root, SearchDto searchDto, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        if(!Optional.ofNullable(searchDto.getSearchKeyword()).orElse("").isEmpty()) {
            predicates.add(builder.like(root.get(searchDto.getSearchType()), "%" + searchDto.getSearchKeyword() + "%"));
        }
        if(!Optional.ofNullable(searchDto.getYnDel()).orElse("").isEmpty()) {
            predicates.add(builder.equal(root.get("ynDel"), searchDto.getYnDel()));
        }

        return predicates;
    }

    /**
     * todo: custom 검색조건 생성 메소드
     * HashMap에 검색할 컬럼, 검색어 쌍을 입력한다.
     * @Example
     *        - hashMap 입력값
     *         hashMap.put("nameAccount", "관리자");
     *         hashMap.put("roleAccount", "ADMIN");
     *        - 생성되는 검색조건
     *        and account_name like '%관리자%'
     *        and account_role = 'ADMIN'
     * @param root
     * @param searchCondition
     * @param builder
     * @return
     */
    public static List<Predicate> getPredicates(Root root, HashMap searchCondition, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        // like
        // equal
        if(Optional.ofNullable(searchCondition.get("mappList")).isPresent()) {
//            predicates.add(builder.equal(root.get("ynDel"), searchDto.getYnDel()));
            predicates.add(
                    builder.in(root.get("mappList")).value(searchCondition.get("mappList"))
            );
        }

        return predicates;
    }

}