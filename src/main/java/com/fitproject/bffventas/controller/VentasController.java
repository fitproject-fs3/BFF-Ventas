package com.fitproject.bffventas.controller;

import com.fitproject.bffventas.client.VentasClient;
import com.fitproject.bffventas.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller BFF-Ventas que proxea las operaciones de ventas y catálogo hacia MS-Ventas.
 *
 * <p>Actúa como Backend For Frontend para la Sucursal Virtual y el eCommerce,
 * reenviando las llamadas a MS-Ventas vía Feign con Circuit Breaker. Si MS-Ventas
 * no responde, el Circuit Breaker activa el fallback definido en
 * {@link com.fitproject.bffventas.client.fallback.VentasClientFallbackFactory}.</p>
 *
 * <p>Base URL: {@code /api/v1}</p>
 *
 * @see VentasClient
 * @see com.fitproject.bffventas.client.fallback.VentasClientFallbackFactory
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VentasController {

    private final VentasClient ventasClient;

    /**
     * Obtiene los modelos de gimnasio disponibles para la venta.
     *
     * @return lista de modelos con {@code available = true}; vacía si MS-Ventas no responde
     */
    @GetMapping("/models")
    public ResponseEntity<List<GymModelDTO>> getModels() {
        return ResponseEntity.ok(ventasClient.getAvailableModels());
    }

    /**
     * Obtiene el catálogo completo de modelos incluyendo los no disponibles.
     *
     * <p>Para uso administrativo desde el panel de ventas.</p>
     *
     * @return lista completa del catálogo; vacía si MS-Ventas no responde
     */
    @GetMapping("/models/all")
    public ResponseEntity<List<GymModelDTO>> getAllModels() {
        return ResponseEntity.ok(ventasClient.getAllModels());
    }

    /**
     * Crea una nueva venta de un módulo de gimnasio.
     *
     * <p>Confirma la venta en MS-Ventas, que a su vez publica un evento
     * {@code sale.confirmed} en RabbitMQ para notificar al comprador por email.</p>
     *
     * @param request datos de la venta (unitId, buyerId, buyerName, buyerEmail)
     * @return venta creada con status 201 Created; {@code null} si MS-Ventas no responde
     */
    @PostMapping("/sales")
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventasClient.createSale(request));
    }

    /**
     * Lista todas las ventas registradas en el sistema.
     *
     * @return lista completa de ventas; vacía si MS-Ventas no responde
     */
    @GetMapping("/sales")
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        return ResponseEntity.ok(ventasClient.getAllSales());
    }

    /**
     * Obtiene las ventas realizadas por un comprador específico.
     *
     * @param buyerId identificador UUID del comprador
     * @return lista de ventas del comprador; vacía si MS-Ventas no responde
     */
    @GetMapping("/sales/buyer/{buyerId}")
    public ResponseEntity<List<SaleDTO>> getSalesByBuyer(@PathVariable String buyerId) {
        return ResponseEntity.ok(ventasClient.getSalesByBuyer(buyerId));
    }
}
