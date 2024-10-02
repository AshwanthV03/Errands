package com.Loop.Erands.Service.ProductServices;

import com.Loop.Erands.DTO.ProductDto.*;
import com.Loop.Erands.DTO.SellerDto.SellerProductsDto;
import com.Loop.Erands.ResponseObjects.SellerProductsResponse;
import com.Loop.Erands.Models.*;
import com.Loop.Erands.Repository.ProductVariantRepository;
import org.slf4j.Logger;
import com.Loop.Erands.Repository.ProductRepository;
import com.Loop.Erands.Service.UserService.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
@Service

public class ProductServiceImplimentation implements ProductService{
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductVariantRepository productVariantRepository;

    @Autowired
    UserService userService;


    @Override
    public Product findProductById(UUID productId) throws Exception {
        Optional<Product> fetchedProduct;
        try{
            fetchedProduct = productRepository.findById(productId);
            if(!fetchedProduct.isPresent()) throw new Exception("Product not founnd with id:" + productId);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return fetchedProduct.get();
    }

    @Override
    public List<Product> findAllProduct() throws Exception {
        List<Product> fetchedProducts;

        try{
            fetchedProducts = productRepository.findAll();
            if (fetchedProducts.isEmpty()) throw new Exception("No products found ");
        }catch (Exception e){
            throw new Exception(e);
        }
        return fetchedProducts;
    }

    @Override
    public ProductPageDto findProductByVariantId(UUID variantId) throws Exception {
        // Fetch the product, throwing an exception if not found
        Product fetchedProduct = productRepository.findProductByVariantId(variantId);
        if (fetchedProduct == null) throw new Exception("Product not found");

        // Get the specific product variant
        ProductVariant productVariant = fetchedProduct.getProductVariants().stream()
                .filter(pv -> pv.getVariantId().equals(variantId))
                .findFirst()
                .orElseThrow(() -> new Exception("Variant not found"));

        // Create and return the ProductPageDto
        return new ProductPageDto(
                fetchedProduct.getProductTitle(),
                fetchedProduct.getProductDescription(),
                fetchedProduct.getProductId(),
                productVariant.getVariantId(),
                productVariant.getImages().stream().toList(),
                productVariant.getPrice(),
                productVariant.getDiscount(),
                productVariant.getStockCount(),
                5,
                fetchedProduct.getReviews(),
                fetchedProduct.getProductVariants()
        );
    }


    @Override
    public List<ProductsDto> searchKeyword(String keyword, Category category,Date from, Date to, Float discount,Float min, Float max, List<Colour> colours,String sort,String dateSort) {
        List<ProductVariant> productVariants = productVariantRepository.search(category,keyword,from,to, discount,min,max,colours,sort,dateSort);

        List<ProductsDto> productsDtos = productVariants.stream().map(pv->{
            ProductsDto productsDto = new ProductsDto();
            productsDto.setProductTitle(pv.getProduct().getProductTitle());
            productsDto.setProductDescription(pv.getProduct().getProductDescription());
            productsDto.setProductId(pv.getProduct().getProductId());
            productsDto.setImages(pv.getImages().stream().toList());
            productsDto.setPrice(pv.getPrice());
            productsDto.setColour(pv.getColour());
            productsDto.setDate(pv.getTimeStamp());
            productsDto.setVariantId(pv.getVariantId());

            return productsDto  ;
        }).collect(Collectors.toList());
        return productsDtos;
    }


    /**
     -------------------------------------------------------------------------------------------------------------------
        METHODS ACCESSED BY SELLER AND ADMINS
     -------------------------------------------------------------------------------------------------------------------
     */

    // Find all Products Variants within the range of Stock count
    @Override
    public List<ProductVariant> FindProductsByStock(int stockCount){
        List<ProductVariant> products = productVariantRepository.findByStockCount(stockCount);
        return products;
    }
    @Override
    public SellerProductsResponse findProductVariantsBySellerId(UUID sellerId, int pageNumber, int pageSize) throws Exception {

        Pageable page = PageRequest.of(pageNumber,pageSize);
        // Fetch all products, variants, and images in a single query
        Page<Product> fetchedProducts = productRepository.findProductsWithVariantsAndImagesBySellerId((sellerId), page);


        // Iterate over fetched products to populate DTOs
        List<SellerProductsDto> response = fetchedProducts.getContent().stream()
                .flatMap(product -> product.getProductVariants().stream().distinct()
                        .map(variant -> {
                            SellerProductsDto sellerProductsDto = new SellerProductsDto(product, variant);
                            List<ProductImage> productImages = new ArrayList<>(variant.getImages());
                            sellerProductsDto.setProductImages(productImages);
                            return sellerProductsDto;
                        })
                )
                .collect(Collectors.toList());

        return new SellerProductsResponse(response,fetchedProducts.getTotalPages(),fetchedProducts.getNumber(),fetchedProducts.getNumberOfElements());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String addProductVariant(ProductVariantDto productVariantDto) throws Exception {
        // Fetch product or throw an exception if it doesn't exist
        Product product = productRepository.findById(productVariantDto.getProductId())
                .orElseThrow(() -> new Exception("Product Id does not exist"));

        // Create new product variant and set fields from the DTO
        ProductVariant newProductVariant = new ProductVariant();
        newProductVariant.setProduct(product);
        newProductVariant.setSize(productVariantDto.getSize());
        newProductVariant.setDiscount(productVariantDto.getDiscount());
        newProductVariant.setStockCount(productVariantDto.getStockCount());
        newProductVariant.setPrice(productVariantDto.getPrice());
        newProductVariant.setColour(productVariantDto.getColour());

        // Add images to the product variant
        List<ProductImage> productImages = productVariantDto.getImages().stream()
                .map(url -> new ProductImage(newProductVariant, url))
                .collect(Collectors.toList());
        newProductVariant.addImages(productImages);

        // Associate the new variant with the product
        product.addVariant(newProductVariant);

        productRepository.save(product);

        return "Product variant added successfully";
    }

    @Override
    @Transactional
    public String updateProductById(UUID productID, UpdateProductDto updateProductDto) throws Exception {
        // Fetch the product, throw an exception if it doesn't exist
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new Exception("Product does not exist"));

        // Update product fields
        product.setProductTitle(updateProductDto.getProductTitle());
        product.setProductDescription(updateProductDto.getProductDescription());
        product.addCategories(updateProductDto.getCategories());

        // Convert the variant ID from String to UUID once
        UUID variantId = UUID.fromString(updateProductDto.getProductVariantId());

        // Find the variant by ID
        ProductVariant variant = product.getProductVariants().stream()
                .filter(v -> v.getVariantId().equals(variantId))
                .findFirst()
                .orElseThrow(() -> new Exception("Variant not found"));

        // Update variant fields
        updateVariantDetails(variant, updateProductDto);
        productRepository.save(product);

        return "Product Updated";
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String createNewProduct(NewProductDto newProductDto) throws Exception {
        Product product = new Product();
        ProductVariant productVariant = createProductVariantFromDto(newProductDto,product);
        User seller = userService.findUserById(newProductDto.getSellerId());

        // Set product details
        product.setSeller(seller);
        product.addVariant(productVariant);
        product.setProductTitle(newProductDto.getProductTitle());
        product.setProductDescription(newProductDto.getProductDescription());
        product.addCategories(newProductDto.getCategories());

        // Save the product with its variants and images
        productRepository.save(product);

        return "Product Created";
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public String createNewProducts(List<NewProductDto> newProductDtos) throws Exception {
        List<Product> products = new ArrayList<>();

        // Iterate over the list of product DTOs
        for (NewProductDto productDto : newProductDtos) {
            // Fetch the seller for the product
            User seller = userService.findUserById(productDto.getSellerId());

            // Initialize the product and its variant
            Product product = new Product();
            ProductVariant productVariant = createProductVariantFromDto(productDto, product);

            // Set product properties from the DTO
            product.setProductTitle(productDto.getProductTitle());
            product.setProductDescription(productDto.getProductDescription());
            product.setSeller(seller);

            // Associate the variant with the product
            product.addVariant(productVariant);

            // Add the product to the list
            products.add(product);
        }

        //Saving all products in one batch
        try {
            productRepository.saveAll(products); // Batch save products and their variants
            return "Created all product entries";
        } catch (Exception e) {
            throw new Exception("Failed to create products: " + e.getMessage(), e);
        }
    }


    @Transactional
    @Override
    public String deleteByProductId(UUID toDeleteId) throws Exception {
        try {
            // Fetch product and throw exception if not found
            Product product = productRepository.findById(toDeleteId)
                    .orElseThrow(() -> new Exception("Product with ID " + toDeleteId + " does not exist"));

            // Delete the product by its ID
            productRepository.deleteById(toDeleteId);
        } catch (Exception e) {
            throw new Exception("Failed to delete product with ID " + toDeleteId, e);
        }
        return "Product with ID " + toDeleteId + " deleted successfully";
    }


    @Override
    @Transactional
    public String deleteByVariantId(UUID variantId) throws Exception{
        try{
            // Delete the product by its Variant ID
            productVariantRepository.deleteByVariantId(variantId);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return "Deleted Variant";
    }

    // This Method is for internal usage across various service implementation logics
    @Override
    public ProductVariant findProductVariantById(UUID productVariantId) throws Exception {
        // Find the ProductVariant by its ID
        ProductVariant fetchedProductVariant = productRepository.findByProductVariantId(productVariantId);
        if (fetchedProductVariant == null) throw  new Exception("Product Variant not found");
        return fetchedProductVariant;
    }

    /**
     -------------------------------------------------------------------------------------------------------------------
        HELPER METHODS FOR MAPPING FIELDS
     -------------------------------------------------------------------------------------------------------------------
     **/

    // Helper method to create ProductVariant and associate images
    private ProductVariant createProductVariantFromDto(NewProductDto productDto, Product product) {
        ProductVariant productVariant = new ProductVariant();
        List<ProductImage> productImages = new ArrayList<>();

        // Add images from DTO to the product variant
        for (String url : productDto.getImageUrls()) {
            ProductImage image = new ProductImage(productVariant, url);
            productImages.add(image);
        }

        productVariant.addImages(productImages);
        productVariant.setSize(productDto.getSize());
        productVariant.setPrice(productDto.getPrice());
        productVariant.setDiscount(productDto.getDiscount());
        productVariant.setStockCount(productDto.getStockCount());
        productVariant.setProduct(product); // Link variant to the product

        return productVariant;
    }
    // Helper method to update variant details
    private void updateVariantDetails(ProductVariant variant, UpdateProductDto updateProductDto) {
        variant.setSize(updateProductDto.getSize());
        variant.setDiscount(updateProductDto.getDiscount());
        variant.setPrice(updateProductDto.getPrice());
        variant.setStockCount(updateProductDto.getStockCount());
        variant.setColour(updateProductDto.getColour());
    }
}
