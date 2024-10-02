package com.Loop.Erands.Controller;

import com.Loop.Erands.DTO.ProductDto.*;
import com.Loop.Erands.Repository.ProductVariantRepository;
import com.Loop.Erands.ResponseObjects.SellerProductsResponse;
import com.Loop.Erands.Models.Category;
import com.Loop.Erands.Models.Colour;
import com.Loop.Erands.Models.Product;
import com.Loop.Erands.Models.ProductVariant;
import com.Loop.Erands.Service.ProductServices.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts() throws Exception {
        List<Product> fetchedProducts = productService.findAllProduct();
        return new ResponseEntity<>(fetchedProducts, HttpStatus.OK);
    }

    @GetMapping("/variant/{variantId}")
    public ResponseEntity<?> getProductByVariantId(@PathVariable UUID variantId) throws Exception {
        ProductPageDto fetchedProduct = productService.findProductByVariantId(variantId);
        return new ResponseEntity<>(fetchedProduct, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID productId) throws Exception {
        Product fetchedProduct = productService.findProductById(productId);
        return new ResponseEntity<>(fetchedProduct, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(value = "category", required = false) Category category,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(value = "discount", required = false) Float discount,
            @RequestParam(value = "min", required = false) Float min,
            @RequestParam(value = "max", required = false) Float max,
            @RequestParam(value="search", required = false) String keyword,
            @RequestParam(value = "colours", required = false) List<Colour> colours,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "dateSort", required = false) String dateSort
    ) {
        try {
            // Parse 'from' and 'to' dates, handling format issues
            Date fromDate = parseDate(from);
            Date toDate = parseDate(to);

            // Search for products using the provided filters
            List<ProductsDto> productsDtos = productService.searchKeyword(
                    keyword, category, fromDate, toDate, discount, min, max, colours, sort, dateSort
            );

            // Return products if found
            return ResponseEntity.ok(productsDtos);

        } catch (ParseException e) {
            // Handle any date parsing errors
            return ResponseEntity.badRequest().body("Invalid date format. Use dd-MM-yyyy.");
        } catch (Exception e) {
            // Handle general errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Helper method to get Date from string
    private Date parseDate(String dateString) throws ParseException {
        if (dateString == null) return null;
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.parse(dateString);
    }

}
