package com.assessments.luckyshop.shop;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.api.dto.response.CreateBillResponse;
import com.assessments.luckyshop.shop.impl.BillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BillServiceTest {

    private BillService billService;

    @BeforeEach
    void setUp() {
        billService = new BillServiceImpl();
    }

    @Test
    void createBill() {
        //given
        String userId = "dummy-user-id";
        long quantity = 1L;
        CreateBillRequest createBillRequest = CreateBillRequest.builder()
                .quantity(quantity)
                .userId(userId)
                .build();

        //when
        CreateBillResponse createBillResponse = billService.createBill(createBillRequest);

        //then
        assertThat(createBillResponse).isNotNull();
    }
}