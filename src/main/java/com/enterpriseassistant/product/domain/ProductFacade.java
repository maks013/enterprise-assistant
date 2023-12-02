package com.enterpriseassistant.product.domain;

import com.enterpriseassistant.product.dto.AddProductDto;
import com.enterpriseassistant.product.dto.ProductDto;
import com.enterpriseassistant.product.exception.ProductNotFound;
import com.enterpriseassistant.product.exception.TakenGtin;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProductFacade {

    private final ProductRepository repository;
    private final ProductMapper productMapper;

    public List<ProductDto> findAllProducts() {
        return repository.findAll()
                .stream()
                .map(Product::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto addNewProduct(AddProductDto addProductDto) {

        existsByGtin(addProductDto.getGtin());
        GtinNumberValidator.checkGtinPattern(addProductDto.getGtin());

        Product product = productMapper.fromAddDto(addProductDto);
        return repository.save(product).toDto();
    }

    public ProductDto getProductById(Integer id) {
        return getProduct(id).toDto();
    }

    public ProductDto getProductByName(String name) {
        return repository.findByName(name)
                .map(Product::toDto)
                .orElseThrow(ProductNotFound::new);
    }

    public ProductDto getProductByGtin(String gtin) {
        return repository.findByGtin(gtin)
                .map(Product::toDto)
                .orElseThrow(ProductNotFound::new);
    }

    public void deleteProduct(Integer id) {
        final Product product = getProduct(id);
        repository.delete(product);
    }

    private Product getProduct(Integer id) {
        return repository.findById(id)
                .orElseThrow(ProductNotFound::new);
    }

    private void existsByGtin(String gtin) {
        if (repository.existsByGtin(gtin)) {
            throw new TakenGtin();
        }
    }
}
