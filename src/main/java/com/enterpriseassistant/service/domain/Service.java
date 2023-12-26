package com.enterpriseassistant.service.domain;

import com.enterpriseassistant.service.dto.ServiceDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal priceGross;
    private BigDecimal priceNet;
    private String imageUrl;
    private String additionalInformation;

    ServiceDto toDto() {
        return ServiceDto.builder()
                .id(id)
                .name(name)
                .priceGross(getAsBigDecimal(priceGross))
                .priceNet(getAsBigDecimal(priceNet))
                .imageUrl(imageUrl)
                .additionalInformation(additionalInformation)
                .build();
    }

    private BigDecimal getAsBigDecimal(BigDecimal price) {
        return price.setScale(2, RoundingMode.HALF_EVEN);
    }

}
