package com.assessments.luckyshop.shop;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.api.dto.response.CreateBillResponse;

public interface BillService {
    CreateBillResponse createBill(CreateBillRequest createBillRequest);
}
