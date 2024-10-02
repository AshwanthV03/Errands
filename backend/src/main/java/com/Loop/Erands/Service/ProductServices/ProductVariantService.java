package com.Loop.Erands.Service.ProductServices;

import com.Loop.Erands.Models.ProductVariant;

import java.util.List;
import java.util.UUID;

public interface ProductVariantService {
    List<ProductVariant> getAllProductVariants();
    List<ProductVariant> getAllProductVariantByProductId(UUID productId) throws Exception;
    List<ProductVariant> getAllProductVariantBySellerId(UUID sellerId) throws Exception;

    String updateProductByProductVariantId(UUID productVariantId);

}
