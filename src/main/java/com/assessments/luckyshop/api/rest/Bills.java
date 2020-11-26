package com.assessments.luckyshop.api.rest;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.api.dto.response.BillResponse;
import com.assessments.luckyshop.bill.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class Bills {
    private final BillService billService;

    /**
     * API to retrieve calculated bill depending to the product/user types and total amount of the eligible products.
     *
     * @param request contains user and productId/product quantity information {@link CreateBillRequest}
     * @return BillResponse
     */
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public BillResponse createBill(@Valid @RequestBody CreateBillRequest request) {
        return billService.createBill(request);
    }
}