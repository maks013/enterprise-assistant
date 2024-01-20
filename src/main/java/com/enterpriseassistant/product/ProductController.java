package com.enterpriseassistant.product;

import com.enterpriseassistant.product.domain.ProductFacade;
import com.enterpriseassistant.product.dto.AddProductDto;
import com.enterpriseassistant.product.dto.ProductDto;
import com.enterpriseassistant.product.dto.UpdateProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
@Api(value = "Product Controller", description = "Moduł Api odpowiadający za produkty")
public class ProductController {

    private final ProductFacade productFacade;

    @PostMapping
    @ApiOperation(value = "Tworzy nowy produkt")
    public ResponseEntity<ProductDto> addNewProduct(@Valid @RequestBody AddProductDto addProductDto) {
        final ProductDto productDto = productFacade.addNewProduct(addProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Zastępuje dane produktu")
    public ResponseEntity<ProductDto> updateService(@PathVariable int id, @Valid @RequestBody UpdateProductDto updateProductDto) {
        final ProductDto productDto = productFacade.updateProduct(updateProductDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(productDto);
    }

    @GetMapping
    @ApiOperation(value = "Pobiera wszystkie produkty")
    public ResponseEntity<List<ProductDto>> findAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productFacade.findAllProducts());
    }

    @GetMapping("/name/{productName}")
    @ApiOperation(value = "Pobiera produkt po nazwie")
    public ResponseEntity<ProductDto> findProduct(@PathVariable String productName) {
        return ResponseEntity.status(HttpStatus.OK).body(productFacade.getProductByName((productName)));
    }

    @GetMapping("/gtin/{productGtin}")
    @ApiOperation(value = "Pobiera produkt po kodzie gtin")
    public ResponseEntity<ProductDto> findProductByGtin(@PathVariable String productGtin) {
        return ResponseEntity.status(HttpStatus.OK).body(productFacade.getProductByGtin((productGtin)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Usuwa produkt")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        productFacade.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
