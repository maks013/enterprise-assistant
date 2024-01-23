package com.enterpriseassistant.feature;

import com.enterpriseassistant.BaseIntegrationTest;
import com.enterpriseassistant.user.dto.LoginResponseDto;
import com.enterpriseassistant.user.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserWantToAddOrderScenarioIntegrationTest extends BaseIntegrationTest implements JsonExamples {

    @Test
    void user_should_add_new_order_all_steps() throws Exception {

        //0. Admin registers and receives 201 response.
        //given, when
        ResultActions adminRegisterRequest = mockMvc.perform(post("/auth/register-admin")
                .content(adminRegisteringData().trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        adminRegisterRequest.andExpect(status().isCreated());

        //0.2 Admin login and receives 200 with JWT token.
        //given, when
        ResultActions adminLoginRequest = mockMvc.perform(post("/auth/login")
                .content(adminLoginData().trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        MvcResult mvcResult = adminLoginRequest.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        LoginResponseDto adminLoginResponseDto = objectMapper.readValue(json, LoginResponseDto.class);
        assertAll(
                () -> assertThat(adminLoginResponseDto.getUsername()).isEqualTo("admin"),
                () -> assertThat(adminLoginResponseDto.getToken()).matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$"))
        );

        final String adminToken = adminLoginResponseDto.getToken();

        //1. The user attempts to obtain a JWT token by sending a POST request.
        //   The system returns an error 401.
        //given, when
        ResultActions failedLoginRequest = mockMvc.perform(post("/auth/login")
                .content(userLoginData().trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        failedLoginRequest
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(badCredentials().trim())).andReturn();

        //2. The user registers and receives a 201 response.
        //given, when
        ResultActions register = mockMvc.perform(post("/auth/register")
                .content(userRegistersData().trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        MvcResult registerResult = register.andExpect(status().isCreated()).andReturn();
        String registerUserBody = registerResult.getResponse().getContentAsString();
        UserDto registerUserDto = objectMapper.readValue(registerUserBody, UserDto.class);

        //3. The user try to log in with not active account
        //given, when
        ResultActions loginBeforeActivation = mockMvc.perform(post("/auth/login")
                .content(userLoginData().trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        loginBeforeActivation
                .andExpect(status().isUnauthorized())
                .andExpect(content().json(accountNotActive().trim())).andReturn();


        //4. Admin activates user account with response 200.
        //given, when
        ResultActions userAccountActivationByAdmin = mockMvc.perform(patch("/users/enable/" + registerUserDto.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON));
        // then
        userAccountActivationByAdmin.andExpect(status().isOk());

        //5. The user tries to obtain a token again with the data username=user, password=password. The system returns a token with a 200 status.
        //given, when
        ResultActions userLoginSuccessfully = mockMvc.perform(post("/auth/login")
                .content(userLoginData().trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        MvcResult loginResult = userLoginSuccessfully.andExpect(status().isOk()).andReturn();
        String loginUserBody = loginResult.getResponse().getContentAsString();
        LoginResponseDto loginUserResponse = objectMapper.readValue(loginUserBody, LoginResponseDto.class);

        final String userToken = loginUserResponse.getToken();

        //6. User add new client and receives isCreated.
        //given, when
        ResultActions addingNewClient = mockMvc.perform(post("/clients")
                .header("Authorization", "Bearer " + userToken)
                .content(clientDataExample().trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        addingNewClient.andExpect(status().isCreated());

        //7. User add new product and receives isCreated.
        //given, when
        ResultActions addingNewProduct = mockMvc.perform(post("/products")
                .header("Authorization", "Bearer " + userToken)
                .content(productDataExample().trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        addingNewProduct.andExpect(status().isCreated());

        //8. User add new service and receives isCreated.
        //given, when
        ResultActions addingNewService = mockMvc.perform(post("/services")
                .header("Authorization", "Bearer " + userToken)
                .content(serviceDataExample().trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        addingNewService.andExpect(status().isCreated());

        //9. User tries to add order without items.
        //given, when
        ResultActions addingOrderWithoutItems = mockMvc.perform(post("/orders")
                .header("Authorization", "Bearer " + userToken)
                .content(orderWithoutItems().trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        addingOrderWithoutItems.andExpect(status().isNotAcceptable());

        //10. User adds order and receives isCreated response.
        //given, when
        ResultActions addingOrderWithoutClient = mockMvc.perform(post("/orders")
                .header("Authorization", "Bearer " + userToken)
                .content(orderExample().trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        //then
        addingOrderWithoutClient.andExpect(status().isCreated());
    }
}
