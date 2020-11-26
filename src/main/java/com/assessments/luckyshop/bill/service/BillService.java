package com.assessments.luckyshop.bill.service;

import com.assessments.luckyshop.api.dto.request.CreateBillRequest;
import com.assessments.luckyshop.api.dto.response.BillResponse;

public interface BillService {
    BillResponse createBill(CreateBillRequest createBillRequest);
}
