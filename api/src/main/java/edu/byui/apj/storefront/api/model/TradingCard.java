package edu.byui.apj.storefront.api.model;

import java.math.BigDecimal;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class TradingCard {
    private Long id;
    private String name;
    private String specialty;
    private String contribution;
    private BigDecimal price;
    private String imageUrl;
}
