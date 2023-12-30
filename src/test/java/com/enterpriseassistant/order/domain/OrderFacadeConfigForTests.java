package com.enterpriseassistant.order.domain;

import com.enterpriseassistant.product.domain.InMemoryProductRepository;
import com.enterpriseassistant.product.domain.ProductFacade;
import com.enterpriseassistant.product.domain.ProductMapperForTests;
import com.enterpriseassistant.service.domain.InMemoryServiceRepository;
import com.enterpriseassistant.service.domain.ServiceFacade;
import com.enterpriseassistant.service.domain.ServiceMapperForTests;
import com.enterpriseassistant.user.domain.InMemoryUserRepository;
import com.enterpriseassistant.user.domain.UserDataValidatorForTests;
import com.enterpriseassistant.user.domain.UserFacade;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class OrderFacadeConfigForTests {

    public OrderFacade orderFacade() {
        final InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        return new OrderFacade(new UserFacade(inMemoryUserRepository, new UserDataValidatorForTests(inMemoryUserRepository), new BCryptPasswordEncoder()),
                new InMemoryOrderRepository(),
                new OrderMapper(new ProductFacade(new InMemoryProductRepository(), new ProductMapperForTests()), new ServiceFacade(new InMemoryServiceRepository(), new ServiceMapperForTests())));
    }
}
