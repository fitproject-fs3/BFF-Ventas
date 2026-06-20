package com.fitproject.bffventas.controller;

import com.fitproject.bffventas.client.VentasClient;
import com.fitproject.bffventas.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VentasController {

    private final VentasClient ventasClient;

    @GetMapping("/models")
    public ResponseEntity<List<GymModelDTO>> getModels() {
        return ResponseEntity.ok(ventasClient.getAvailableModels());
    }

    @GetMapping("/models/all")
    public ResponseEntity<List<GymModelDTO>> getAllModels() {
        return ResponseEntity.ok(ventasClient.getAllModels());
    }

    @PostMapping("/sales")
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventasClient.createSale(request));
    }

    @GetMapping("/sales")
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        return ResponseEntity.ok(ventasClient.getAllSales());
    }

    @GetMapping("/sales/buyer/{buyerId}")
    public ResponseEntity<List<SaleDTO>> getSalesByBuyer(@PathVariable String buyerId) {
        return ResponseEntity.ok(ventasClient.getSalesByBuyer(buyerId));
    }
}