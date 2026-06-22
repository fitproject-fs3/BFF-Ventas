package com.fitproject.bffventas.client.fallback;

import com.fitproject.bffventas.client.VentasClient;
import com.fitproject.bffventas.dto.GymModelDTO;
import com.fitproject.bffventas.dto.SaleDTO;
import com.fitproject.bffventas.dto.SaleRequestDTO;
import feign.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Fallback Factory para {@link VentasClient}.
 *
 * <p>Proporciona degradación segura cuando MS-Ventas no está disponible.
 * El catálogo de modelos retorna lista vacía; la creación de venta retorna
 * {@code null} para que el controlador informe la indisponibilidad al cliente.</p>
 *
 * @see VentasClient
 */
@Slf4j
@Component
public class VentasClientFallbackFactory implements FallbackFactory<VentasClient> {

    /**
     * Crea una instancia fallback de {@link VentasClient} que registra el fallo
     * y aplica degradación segura por método.
     *
     * @param cause excepción que activó el circuit breaker
     * @return implementación de degradación de {@link VentasClient}
     */
    @Override
    public VentasClient create(Throwable cause) {
        log.error("[CircuitBreaker] MS-Ventas no disponible: {}", cause.getMessage());
        return new VentasClient() {

            @Override
            public List<GymModelDTO> getAvailableModels() {
                log.warn("[Fallback] getAvailableModels → lista vacía");
                return Collections.emptyList();
            }

            @Override
            public List<GymModelDTO> getAllModels() {
                log.warn("[Fallback] getAllModels → lista vacía");
                return Collections.emptyList();
            }

            @Override
            public SaleDTO createSale(SaleRequestDTO request) {
                log.warn("[Fallback] createSale → null (servicio no disponible)");
                return null;
            }

            @Override
            public List<SaleDTO> getAllSales() {
                log.warn("[Fallback] getAllSales → lista vacía");
                return Collections.emptyList();
            }

            @Override
            public List<SaleDTO> getSalesByBuyer(String buyerId) {
                log.warn("[Fallback] getSalesByBuyer({}) → lista vacía", buyerId);
                return Collections.emptyList();
            }
        };
    }
}
