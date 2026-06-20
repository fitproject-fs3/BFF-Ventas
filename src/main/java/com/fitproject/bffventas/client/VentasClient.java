package com.fitproject.bffventas.client;

import com.fitproject.bffventas.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ms-ventas", url = "${ms.ventas.url}")
public interface VentasClient {

    @GetMapping("/api/v1/models")
    List<GymModelDTO> getAvailableModels();

    @GetMapping("/api/v1/models/all")
    List<GymModelDTO> getAllModels();

    @PostMapping("/api/v1/sales")
    SaleDTO createSale(@RequestBody SaleRequestDTO request);

    @GetMapping("/api/v1/sales")
    List<SaleDTO> getAllSales();

    @GetMapping("/api/v1/sales/buyer/{buyerId}")
    List<SaleDTO> getSalesByBuyer(@PathVariable("buyerId") String buyerId);
}