package com.fitproject.bffventas.dto;

import lombok.Data;

@Data
public class SaleRequestDTO {
    private String unitId;
    private String buyerName;
    private String buyerEmail;
    private String buyerId;
}