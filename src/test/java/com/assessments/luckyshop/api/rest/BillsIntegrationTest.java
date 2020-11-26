package com.assessments.luckyshop.api.rest;

import com.assessments.luckyshop.LuckyShopApplication;
import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.api.dto.request.ProductCount;
import com.assessments.luckyshop.infrastructure.model.ApplicationErrorCode;
import com.assessments.luckyshop.product.repository.ProductRepository;
import com.assessments.luckyshop.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {LuckyShopApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Rollback
@AutoConfigureMockMvc
@Sql(scripts = {"/initial_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
class BillsIntegrationTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should calculate discounts over the affiliate discount rule.")
    void createBill_withEligibleAffiliateDiscountRequest_shouldPass() throws Exception {
        //given
        List<ProductCount> products = List.of(
                ProductCount.builder().quantity(1).productId("1e5242eb-7ed9-42b3-af00-e341eff621f9").build(),
                ProductCount.builder().quantity(2).productId("2d710541-5d02-4f5c-b3e1-cbacd2c21abe").build()
        );
        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .userId("80f30f1c-de8f-4a7e-a873-3599f1871450")
                .products(products)
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBillRequest))
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        //then
        assertThat(mvcResult)
                .isNotNull();

        JSONAssert.assertEquals(
                "{ \"amount\": 20.00, \"discountAmount\": 2.00}",
                mvcResult.getResponse().getContentAsString(),
                JSONCompareMode.LENIENT
        );
    }

    @Test
    @DisplayName("should calculate discounts over the employee discount rule.")
    void createBill_withEligibleEmployeeDiscountRequest_shouldPass() throws Exception {
        //given
        List<ProductCount> products = List.of(
                ProductCount.builder().quantity(1).productId("1e5242eb-7ed9-42b3-af00-e341eff621f9").build(),
                ProductCount.builder().quantity(2).productId("2d710541-5d02-4f5c-b3e1-cbacd2c21abe").build()
        );
        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .userId("2d6938b0-3e5c-453c-aa91-7d1ffde5734f")
                .products(products)
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBillRequest))
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        //then
        assertThat(mvcResult)
                .isNotNull();

        JSONAssert.assertEquals(
                "{ \"amount\": 20.00, \"discountAmount\": 6.00}",
                mvcResult.getResponse().getContentAsString(),
                JSONCompareMode.LENIENT
        );
    }

    @Test
    @DisplayName("should calculate discounts over the loyalty discount rule.")
    void createBill_withEligibleLoyaltyDiscountRequest_shouldPass() throws Exception {
        //given
        List<ProductCount> products = List.of(
                ProductCount.builder().quantity(1).productId("1e5242eb-7ed9-42b3-af00-e341eff621f9").build(),
                ProductCount.builder().quantity(2).productId("2d710541-5d02-4f5c-b3e1-cbacd2c21abe").build()
        );
        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .userId("0c68f2a6-ec52-4dd1-bf76-891e564ebe9e")
                .products(products)
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBillRequest))
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        //then
        assertThat(mvcResult)
                .isNotNull();

        JSONAssert.assertEquals(
                "{ \"amount\": 20.00, \"discountAmount\": 1.00}",
                mvcResult.getResponse().getContentAsString(),
                JSONCompareMode.LENIENT
        );
    }

    @Test
    @DisplayName("should not calculate any discounts since no discounts eligible for the request.")
    void createBill_withNotEligibleLoyaltyDiscountRequest_shouldPass() throws Exception {
        //given
        List<ProductCount> products = List.of(
                ProductCount.builder().quantity(1).productId("1e5242eb-7ed9-42b3-af00-e341eff621f9").build(),
                ProductCount.builder().quantity(2).productId("2d710541-5d02-4f5c-b3e1-cbacd2c21abe").build()
        );
        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .userId("50ab042b-18af-4d58-ba6b-df100da856fd")
                .products(products)
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBillRequest))
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        //then
        assertThat(mvcResult)
                .isNotNull();

        JSONAssert.assertEquals(
                "{ \"amount\": 20.00, \"discountAmount\": 0}",
                mvcResult.getResponse().getContentAsString(),
                JSONCompareMode.LENIENT
        );
    }

    @Test
    @DisplayName("should calculate discounts over the amount discount rule.")
    void createBill_withEligibleAmountDiscountRequest_shouldPass() throws Exception {
        //given
        List<ProductCount> products = List.of(
                ProductCount.builder().quantity(1).productId("1e5242eb-7ed9-42b3-af00-e341eff621f9").build(),
                ProductCount.builder().quantity(17).productId("2d710541-5d02-4f5c-b3e1-cbacd2c21abe").build(),
                ProductCount.builder().quantity(20).productId("bfbfa9f8-fdef-4731-b257-a6fefa1efec5").build()
        );
        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .userId("50ab042b-18af-4d58-ba6b-df100da856fd")
                .products(products)
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBillRequest))
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        //then
        assertThat(mvcResult)
                .isNotNull();

        JSONAssert.assertEquals(
                "{ \"amount\": 495.00, \"discountAmount\": 20.00}",
                mvcResult.getResponse().getContentAsString(),
                JSONCompareMode.LENIENT
        );
    }

    @Test
    @DisplayName("should calculate discounts over the amount discount rule and the loyalty discount rule.")
    void createBill_withEligibleAmountAndLoyaltyDiscountsRequest_shouldPass() throws Exception {
        //given
        List<ProductCount> products = List.of(
                ProductCount.builder().quantity(1).productId("1e5242eb-7ed9-42b3-af00-e341eff621f9").build(),
                ProductCount.builder().quantity(17).productId("2d710541-5d02-4f5c-b3e1-cbacd2c21abe").build(),
                ProductCount.builder().quantity(20).productId("bfbfa9f8-fdef-4731-b257-a6fefa1efec5").build()
        );
        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .userId("0c68f2a6-ec52-4dd1-bf76-891e564ebe9e")
                .products(products)
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(
                post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBillRequest))
        )
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        //then
        assertThat(mvcResult)
                .isNotNull();

        JSONAssert.assertEquals(
                "{ \"amount\": 495.00, \"discountAmount\": 43.75}",
                mvcResult.getResponse().getContentAsString(),
                JSONCompareMode.LENIENT
        );
    }

    @Test
    @DisplayName("should reject the request if products field is empty.")
    void answerTest_withEmptyProductsParameter_shouldFail() throws Exception {
        //when
        List<ProductCount> products = List.of();
        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .userId("0c68f2a6-ec52-4dd1-bf76-891e564ebe9e")
                .products(products)
                .build();

        ResultActions resultActions = mockMvc.perform(
                post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBillRequest))
        );

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", org.hamcrest.Matchers.containsString("on field 'products': rejected value [" + products + "];")))
                .andDo(print());
    }

    @Test
    @DisplayName("should reject the request if quantity is little then 1.")
    void answerTest_withInvalidQuantityParameter_shouldFail() throws Exception {
        //when
        List<ProductCount> products = List.of(ProductCount.builder().productId("1e5242eb-7ed9-42b3-af00-e341eff621f9").build());
        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .userId("0c68f2a6-ec52-4dd1-bf76-891e564ebe9e")
                .products(products)
                .build();

        ResultActions resultActions = mockMvc.perform(
                post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBillRequest))
        );

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", org.hamcrest.Matchers.containsString("on field 'products[0].quantity': rejected value [0]; ")))
                .andDo(print());
    }

    @Test
    @DisplayName("should reject the request if productId is empty/null.")
    void answerTest_withBlankProductIdParameter_shouldFail() throws Exception {
        //when
        List<ProductCount> products = List.of(ProductCount.builder().productId("").quantity(1).build());
        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .userId("0c68f2a6-ec52-4dd1-bf76-891e564ebe9e")
                .products(products)
                .build();

        ResultActions resultActions = mockMvc.perform(
                post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBillRequest))
        );

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", org.hamcrest.Matchers.containsString("on field 'products[0].productId': rejected value []; ")))
                .andDo(print());
    }

    @Test
    @DisplayName("should fail since the product not found.")
    void answerTest_withInvalidProductIdParameter_shouldFail() throws Exception {
        //when
        List<ProductCount> products = List.of(ProductCount.builder().productId("dummy-product-id").quantity(1).build());
        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .userId("0c68f2a6-ec52-4dd1-bf76-891e564ebe9e")
                .products(products)
                .build();

        ResultActions resultActions = mockMvc.perform(
                post("/bills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBillRequest))
        );

        //then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", org.hamcrest.Matchers.is(ApplicationErrorCode.NOT_FOUND.getMessage())))
                .andDo(print());
    }
}