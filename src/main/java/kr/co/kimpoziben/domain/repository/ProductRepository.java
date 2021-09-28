package kr.co.kimpoziben.domain.repository;

import kr.co.kimpoziben.domain.entity.Notice;
import kr.co.kimpoziben.domain.entity.Product;
import kr.co.kimpoziben.domain.repository.dsl.ProductRepositoryCustom;
import kr.co.kimpoziben.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, ProductRepositoryCustom {

    @Query(nativeQuery = true
            , value = "select product0_.product_seq as product_seq, product0_.price_buy as price_buy, product0_.product_color as product_color\n" +
                            "     , product0_.product_desc as product_desc, product0_.main_img_id as main_img_id, product0_.moddt as moddt\n" +
                            "     , product0_.modifier as modifier, product0_.product_nm as product_nm, product0_.regdt as regdt\n" +
                            "     , product0_.register as register, product0_.price_sell as price_sell, product0_.product_title as product_title\n" +
                            "     , product0_.vendor_seq as vendor_seq, product0_.display_yn as display_yn, product0_.soldout_yn as soldout_yn \n" +
                            "     , b.cnt as order_cnt\n" +
                            "  from gps_product product0_\n" +
                            "     , (\n" +
                            "      select product_seq , count(1) as cnt\n" +
                            "\t\tfrom (\n" +
                            "\t\t\tselect DISTINCT order_seq, product_seq \n" +
                            "\t\t\tfrom gps_order_product gop \n" +
                            "\t\t) as t1\n" +
                            "\t\tgroup by product_seq \n" +
                            "\t\torder by count(1) desc \n" +
                            "\t\tlimit 4\n" +
                            "     ) b \n" +
                            " where product0_.product_seq = b.product_seq\n" +
                            " and product0_.display_yn='Y' \n" +
                            " order by b.cnt desc"
    )
    List<Product> findByOrderCnt();
}
