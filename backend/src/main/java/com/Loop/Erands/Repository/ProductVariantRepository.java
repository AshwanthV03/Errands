package com.Loop.Erands.Repository;

import com.Loop.Erands.Models.Category;
import com.Loop.Erands.Models.Colour;
import com.Loop.Erands.Models.ProductVariant;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {

    @Query("SELECT pv FROM ProductVariant pv JOIN pv.product p WHERE :category IN elements(p.categories)")
    List<ProductVariant> findByCategory(@Param("category") Category category, Pageable pageable);
    @Query("SELECT pv FROM ProductVariant pv JOIN pv.product p WHERE :category IN elements(p.categories)")
    List<ProductVariant> findByCategory(@Param("category") Category category);
    @Query("SELECT pv FROM ProductVariant pv JOIN FETCH pv.product P WHERE pv.stockCount <= :count")
    List<ProductVariant> findByStockCount(@Param("count")int count);
    @Transactional
    @Modifying
    @Query("DELETE FROM ProductVariant pv WHERE pv.variantId = :variantId")
    void deleteByVariantId(@Param("variantId") UUID variantId);

    @Query("SELECT pv FROM ProductVariant pv " +
            "LEFT JOIN pv.product p " +
            "LEFT JOIN p.categories " +
            "WHERE pv.variantId = :variantId")
    List<ProductVariant> test(@Param("variantId")UUID variantId);

    @Query("SELECT pv FROM ProductVariant pv " +
            "JOIN FETCH pv.product p " +
            "JOIN FETCH p.categories c " +
            "WHERE (:category IS NULL OR :category IN elements(p.categories)) " +
            "AND (:keyword IS NULL OR (LOWER(p.productTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.productDescription) LIKE LOWER(CONCAT('%', :keyword, '%')))) " +
            "AND (:from IS NULL OR pv.timeStamp >= :from) " +
            "AND (:to IS NULL OR pv.timeStamp <= :to) " +
            "AND (:discount IS NULL OR pv.discount >= :discount) " +
            "AND (:min IS NULL OR pv.price >= :min) " +
            "AND (:max IS NULL OR pv.price <= :max) " +
            "AND (:colour IS NULL OR pv.colour IN :colour) " + // Colour filtering
            "ORDER BY " +
            "CASE WHEN :sort IS NOT NULL AND :sort = 'asc' THEN pv.price END ASC, " +     // Sort by Price Ascending
            "CASE WHEN :sort IS NOT NULL AND :sort = 'desc' THEN pv.price END DESC, " +   // Sort by Price Descending
            "CASE WHEN :sortDate IS NOT NULL AND :sortDate = 'asc' THEN pv.timeStamp END ASC, " +  // Sort by Timestamp Ascending
            "CASE WHEN :sortDate IS NOT NULL AND :sortDate = 'desc' THEN pv.timeStamp END DESC"    // Sort by Timestamp Descending
    )
    List<ProductVariant> search(Category category, String keyword, Date from, Date to, Float discount, Float min, Float max, List<Colour> colour, String sort, String sortDate);


}

