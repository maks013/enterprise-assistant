package com.enterpriseassistant.product.domain;

import com.enterpriseassistant.product.dto.AddProductDto;
import com.enterpriseassistant.product.dto.ProductDto;
import com.enterpriseassistant.product.dto.UpdateProductDto;
import com.enterpriseassistant.product.exception.InvalidGtinNumberFormat;
import com.enterpriseassistant.product.exception.ProductNotFound;
import com.enterpriseassistant.product.exception.TakenGtin;
import com.enterpriseassistant.service.dto.ServiceDto;
import com.enterpriseassistant.service.exception.ServiceNotFound;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class ProductFacadeTest {

    private final ProductFacade productFacade = new ProductFacade(new InMemoryProductRepository(), new ProductMapper());

    @Test
    void should_find_all_products_and_size_should_equals_2() {
        //given
        final int size = productFacade.findAllProducts().size();
        //when
        //then
        assertEquals(2, size);
    }

    @Test
    void should_add_product_properly() {
        //given
        AddProductDto product = AddProductDto.builder()
                .gtin("0123452783012")
                .name("New product")
                .priceNet(BigDecimal.valueOf(100))
                .imageUrl("https://example.com/product-image.jpg")
                .additionalInformation("information")
                .build();
        //when
        final int sizeBeforeAddingProduct = productFacade.findAllProducts().size();
        com.enterpriseassistant.product.dto.ProductDto addedProduct = productFacade.addNewProduct(product);
        final int sizeAfterAddingProduct = productFacade.findAllProducts().size();

        //then
        assertAll(
                () -> assertEquals(1, sizeAfterAddingProduct - sizeBeforeAddingProduct),
                () -> assertEquals(product.getGtin(), addedProduct.getGtin()),
                () -> assertEquals(product.getName(), addedProduct.getName()),
                () -> assertEquals(product.getPriceNet().setScale(2), addedProduct.getPriceNet()),
                () -> assertEquals(BigDecimal.valueOf(123).setScale(2), addedProduct.getPriceGross()),
                () -> assertEquals(product.getImageUrl(), addedProduct.getImageUrl()),
                () -> assertEquals(product.getAdditionalInformation(), addedProduct.getAdditionalInformation())
        );
    }

    @Test
    void should_throw_exception_when_adding_a_product_with_an_incorrect_pattern_of_gtinNumber() {
        //given
        final String incorrectGtin = "012345";
        //when
        AddProductDto product = AddProductDto.builder()
                .gtin(incorrectGtin)
                .name("Example")
                .priceNet(BigDecimal.valueOf(100))
                .imageUrl("https://example.com/product-image.jpg")
                .additionalInformation("example information")
                .build();
        //then
        assertThrows(InvalidGtinNumberFormat.class, () -> productFacade.addNewProduct(product),
                "Format of gtin number is incorrect");
    }

    @Test
    void should_throw_exception_when_adding_a_product_with_an_existing_gtinNumber() {
        //given
        /*
          existing gtin in memory repo = 0123456789012
        */
        final String existingGtin = "0123456789012";
        //when
        AddProductDto product = AddProductDto.builder()
                .gtin(existingGtin)
                .name("Example")
                .priceNet(BigDecimal.valueOf(100))
                .imageUrl("https://example.com/product-image.jpg")
                .additionalInformation("example information")
                .build();
        //then
        assertThrows(TakenGtin.class, () -> productFacade.addNewProduct(product),
                "Product with this gtin number already exists");
    }

    @Test
    void should_throw_exception_when_get_product_by_not_existing_name() {
        //given
        final String notExistingNameOfProduct = "notExistingNameOfProduct";
        //when
        //then
        assertThrows(ProductNotFound.class, () -> productFacade.getProductByName(notExistingNameOfProduct),
                "Product not found");
    }

    @Test
    void should_get_product_by_name() {
        //given
        final String name = "New product";
        AddProductDto product = AddProductDto.builder()
                .gtin("0123452783012")
                .name(name)
                .priceNet(BigDecimal.valueOf(100))
                .imageUrl("https://example.com/product-image.jpg")
                .additionalInformation("information")
                .build();
        //when
        com.enterpriseassistant.product.dto.ProductDto addedProduct = productFacade.addNewProduct(product);
        com.enterpriseassistant.product.dto.ProductDto productDto = productFacade.getProductByName(name);
        //then
        assertAll(
                () -> assertEquals(addedProduct.getName(), productDto.getName()),
                () -> assertEquals(addedProduct.getGtin(), productDto.getGtin()),
                () -> assertEquals(addedProduct.getPriceNet(), productDto.getPriceNet()),
                () -> assertEquals(addedProduct.getPriceGross(), productDto.getPriceGross()),
                () -> assertEquals(addedProduct.getAdditionalInformation(), productDto.getAdditionalInformation())
        );
    }

    @Test
    void should_get_product_by_id() {
        //given
        final String name = "New product";
        AddProductDto product = AddProductDto.builder()
                .gtin("0123452783012")
                .name(name)
                .priceNet(BigDecimal.valueOf(100))
                .imageUrl("https://example.com/product-image.jpg")
                .additionalInformation("information")
                .build();
        //when
        com.enterpriseassistant.product.dto.ProductDto addedProduct = productFacade.addNewProduct(product);
        com.enterpriseassistant.product.dto.ProductDto productDto = productFacade.getProductById(addedProduct.getId());
        //then
        assertAll(
                () -> assertEquals(addedProduct.getName(), productDto.getName()),
                () -> assertEquals(addedProduct.getGtin(), productDto.getGtin()),
                () -> assertEquals(addedProduct.getPriceNet(), productDto.getPriceNet()),
                () -> assertEquals(addedProduct.getPriceGross(), productDto.getPriceGross()),
                () -> assertEquals(addedProduct.getAdditionalInformation(), productDto.getAdditionalInformation())
        );
    }

    @Test
    void should_throw_exception_when_get_product_by_not_existing_gtin() {
        //given
        final String notExistingGtin = "0123452723032";
        //when
        //then
        assertThrows(ProductNotFound.class, () -> productFacade.getProductByGtin(notExistingGtin),
                "Product not found");
    }

    @Test
    void should_get_product_by_gtin() {
        //given
        final String gtin = "0123452783012";
        AddProductDto product = AddProductDto.builder()
                .gtin(gtin)
                .name("name")
                .priceNet(BigDecimal.valueOf(100))
                .imageUrl("https://example.com/product-image.jpg")
                .additionalInformation("information")
                .build();
        //when
        com.enterpriseassistant.product.dto.ProductDto addedProduct = productFacade.addNewProduct(product);
        com.enterpriseassistant.product.dto.ProductDto productDto = productFacade.getProductByGtin(gtin);
        //then
        assertAll(
                () -> assertEquals(addedProduct.getName(), productDto.getName()),
                () -> assertEquals(addedProduct.getGtin(), productDto.getGtin()),
                () -> assertEquals(addedProduct.getPriceNet(), productDto.getPriceNet()),
                () -> assertEquals(addedProduct.getPriceGross(), productDto.getPriceGross()),
                () -> assertEquals(addedProduct.getAdditionalInformation(), productDto.getAdditionalInformation())
        );
    }

    @Test
    void should_throw_exception_while_deleting_product_by_not_existing_id() {
        //given
        final int notExistingId = 99;
        //when
        //then
        assertThrows(ProductNotFound.class, () -> productFacade.deleteProduct(notExistingId),
                "Product not found");
    }

    @Test
    void should_delete_product_properly() {
        //given
        final int productId = 1;
        //when
        final int sizeBeforeDeleting = productFacade.findAllProducts().size();
        productFacade.deleteProduct(productId);
        final int sizeAfterDeleting = productFacade.findAllProducts().size();
        //then
        assertEquals(1, sizeBeforeDeleting - sizeAfterDeleting);
    }

    @Test
    void should_throw_exception_when_update_not_existing_product() {
        //given
        final int notExistingProductId = 999;
        com.enterpriseassistant.product.dto.UpdateProductDto updateProductDto = com.enterpriseassistant.product.dto.UpdateProductDto.builder()
                .name("Updated")
                .priceNet(BigDecimal.valueOf(1000))
                .additionalInformation("Updated information")
                .build();
        //when
        //then
        assertThrows(ProductNotFound.class, () -> productFacade.updateProduct(updateProductDto, notExistingProductId));
    }

    @Test
    void should_throw_exception_when_update_with_empty_gtin() {
        //given
        com.enterpriseassistant.product.dto.UpdateProductDto updateProductDto = com.enterpriseassistant.product.dto.UpdateProductDto.builder()
                .gtin("")
                .name("Updated")
                .priceNet(BigDecimal.valueOf(1000))
                .additionalInformation("Updated information")
                .build();
        //when
        //then
        assertThrows(InvalidGtinNumberFormat.class, () -> productFacade.updateProduct(updateProductDto, 1));
    }

    @Test
    void should_throw_exception_when_update_with_already_existing_gtin() {
        //given
        com.enterpriseassistant.product.dto.UpdateProductDto updateProductDto = com.enterpriseassistant.product.dto.UpdateProductDto.builder()
                .gtin("0123456789012")
                .name("Updated")
                .priceNet(BigDecimal.valueOf(1000))
                .additionalInformation("Updated information")
                .build();
        //when
        //then
        assertThrows(TakenGtin.class, () -> productFacade.updateProduct(updateProductDto, 1));
    }

    @Test
    void should_update_product_properly() {
        //given
        UpdateProductDto updateProductDto = UpdateProductDto.builder()
                .name("Updated")
                .priceNet(BigDecimal.valueOf(1000))
                .additionalInformation("Updated information")
                .build();
        //when
        ProductDto productDto = productFacade.updateProduct(updateProductDto, 1);
        //then
        assertAll(
                () -> assertEquals(updateProductDto.getName(), productDto.getName()),
                () -> assertEquals(updateProductDto.getPriceNet().setScale(2, RoundingMode.HALF_EVEN), productDto.getPriceNet()),
                () -> assertEquals(updateProductDto.getAdditionalInformation(), productDto.getAdditionalInformation())
        );
    }
}
