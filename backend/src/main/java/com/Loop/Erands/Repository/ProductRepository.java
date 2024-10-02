package com.Loop.Erands.Repository;

import com.Loop.Erands.DTO.SellerDto.SellerProductsDto;
import com.Loop.Erands.Models.Category;
import com.Loop.Erands.Models.Product;
import com.Loop.Erands.Models.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT new com.Loop.Erands.DTO.SellerDto.SellerProductsDto(p.productId, p.productTitle, p.productDescription, " +
            "pv.variantId, pv.size, pv.stockCount, pv.price, pv.discount) " +
            "FROM Product p JOIN p.productVariants pv WHERE p.seller.userId = :sellerUserId")
    Set<SellerProductsDto> findProductVariantsBySellerId(@Param("sellerUserId") UUID sellerUserId);
    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.productVariants pv " +
            "LEFT JOIN FETCH pv.images " +
            "WHERE p.seller.id = :sellerId")
    Page<Product> findProductsWithVariantsAndImagesBySellerId(@Param("sellerId") UUID sellerId, Pageable page);

    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN FETCH p.productVariants pv " +
            "LEFT JOIN FETCH pv.images " +
            "WHERE p.productId = (SELECT pv2.product.productId FROM ProductVariant pv2 WHERE pv2.variantId = :variantId)")
    Product findProductByVariantId(@Param("variantId") UUID variantId);

    @Query("SELECT pv FROM ProductVariant pv WHERE pv.variantId =:variantId")
    ProductVariant findByProductVariantId(@Param("variantId") UUID variantId);

    @Query(value = "SELECT DISTINCT p.* FROM product p " +
            "LEFT JOIN product_variant pv ON pv.p_id = p.product_id " +
            "LEFT JOIN product_category pc ON pc.product_id = p.product_id " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR " +
            "(LOWER(REGEXP_REPLACE(p.product_title, '[^a-zA-Z0-9 ]', '')) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(REGEXP_REPLACE(p.product_description, '[^a-zA-Z0-9 ]', '')) LIKE LOWER(CONCAT('%', :keyword, '%'))))",
            nativeQuery = true)
    List<Product> searchByKeyword(
            @Param("keyword") String keyword
    );











}
