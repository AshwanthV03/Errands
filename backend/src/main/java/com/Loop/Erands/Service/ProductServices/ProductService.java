package com.Loop.Erands.Service.ProductServices;

import com.Loop.Erands.DTO.ProductDto.*;
import com.Loop.Erands.ResponseObjects.SellerProductsResponse;
import com.Loop.Erands.Models.Category;
import com.Loop.Erands.Models.Colour;
import com.Loop.Erands.Models.Product;
import com.Loop.Erands.Models.ProductVariant;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    /**
     * Creates a new product based on the provided data transfer object (DTO).
     *
     * @param newProductDto DTO containing the details of the new product.
     * @return A message indicating the outcome of the operation.
     * @throws Exception if there is an error during product creation.
     */
    public String createNewProduct(NewProductDto newProductDto ) throws Exception;

    /**
     * Creates multiple new products based on the provided list of DTOs.
     *
     * @param newProductDtos List of DTOs containing the details of new products.
     * @return A message indicating the outcome of the operation.
     * @throws Exception if there is an error during product creation.
     */
    @Transactional(rollbackOn = Exception.class)
    public String createNewProducts(List<NewProductDto> newProductDtos) throws Exception;

    /**
     * Adds a new variant to an existing product.
     *
     * @param productVariantDto DTO containing the details of the product variant.
     * @return A message indicating the outcome of the operation.
     * @throws Exception if the product or variant cannot be found or there is an error.
     */
    public String addProductVariant(ProductVariantDto productVariantDto) throws  Exception;

    /**
     * Deletes a product by its ID.
     *
     * @param toDeleteId The ID of the product to delete.
     * @return A message indicating the outcome of the deletion.
     * @throws Exception if the product cannot be found or there is an error during deletion.
     */
    public String deleteByProductId(UUID toDeleteId) throws Exception;

    /**
     * Updates an existing product by its ID using the provided DTO.
     *
     * @param productID The ID of the product to update.
     * @param updateProductDto DTO containing the updated product details.
     * @return A message indicating the outcome of the operation.
     * @throws Exception if the product cannot be found or there is an error during update.
     */
    public String updateProductById(UUID productID, UpdateProductDto updateProductDto) throws Exception;

    /**
     * Finds a product by its ID.
     *
     * @param productId The ID of the product to find.
     * @return The found product.
     * @throws Exception if the product cannot be found.
     */
    public Product findProductById(UUID productId) throws Exception;

    /**
     * Retrieves a list of all products.
     *
     * @return A list of all products.
     * @throws Exception if there is an error during retrieval.
     */
    public List<Product> findAllProduct() throws Exception;

    /**
     * Finds a product variant by its ID.
     *
     * @param productVariantId The ID of the product variant to find.
     * @return The found product variant.
     * @throws Exception if the variant cannot be found.
     */
    public ProductVariant findProductVariantById (UUID productVariantId) throws Exception;

    /**
     * Finds all product variants for a given seller ID, with pagination.
     *
     * @param sellerId The ID of the seller whose products are being retrieved.
     * @param pageNumber The page number for pagination.
     * @param pageSize The number of products per page.
     * @return A response object containing the seller's products.
     * @throws Exception if there is an error during retrieval.
     */
    public SellerProductsResponse findProductVariantsBySellerId(UUID sellerId, int pageNumber, int pageSize) throws Exception;

    /**
     * Finds a product by its variant ID.
     *
     * @param variantId The ID of the variant to find.
     * @return A DTO representing the product and its variant.
     * @throws Exception if the product or variant cannot be found.
     */
    public ProductPageDto findProductByVariantId(UUID variantId) throws Exception;

    /**
     * Searches for products based on additional date-related criteria.
     *
     * @param keyword The search keyword.
     * @param category The category of products to search in.
     * @param from The start date for filtering.
     * @param to The end date for filtering.
     * @param discount The discount filter.
     * @param min The minimum price filter.
     * @param max The maximum price filter.
     * @param colours The list of allowed colours.
     * @param sort The sorting criteria.
     * @param dateSort The date sorting criteria.
     * @return A list of products matching the search criteria.
     */
    List<ProductsDto> searchKeyword(String keyword, Category category, Date from, Date to, Float discount, Float min, Float max, List<Colour> colours, String sort, String dateSort);

    /**
     * Finds products that have a stock count below a specified number.
     *
     * @param stockCount The stock count threshold.
     * @return A list of product variants that meet the stock criteria.
     */
    public List<ProductVariant> FindProductsByStock(int stockCount);

    /**
     * Deletes a product variant by its ID.
     *
     * @param variantId The ID of the product variant to delete.
     * @return A message indicating the outcome of the deletion.
     * @throws Exception if the variant cannot be found or there is an error during deletion.
     */
    String deleteByVariantId(UUID variantId) throws Exception;
}
