package com.assessments.shopping.bill.service;

import com.assessments.shopping.api.dto.request.CreateBillRequest;
import com.assessments.shopping.api.dto.response.BillResponse;

public interface BillService {
    BillResponse createBill(CreateBillRequest createBillRequest);
}
