package com.example.credit.billing.service;

import com.example.credit.billing.Dto.BillRecordDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BillRecordService {

    //TODO: method to get all invoices in pagination manner both as buyer and seller
    public Page<BillRecordDto> getInvoices(){
        // using UUID to get userId
        // then using that userID to find all invoice as buyer and seller
        return null;
    }
}
