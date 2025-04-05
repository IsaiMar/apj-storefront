package edu.byui.apj.storefront.api.controller;


import edu.byui.apj.storefront.api.model.TradingCard;
import edu.byui.apj.storefront.api.service.TradingCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class TradingCardController {

    private final TradingCardService tradingCardService;

    @Autowired
    public TradingCardController(TradingCardService tradingCardService) {
        this.tradingCardService = tradingCardService;
    }

    // GET /api/cards?page=0&size=20
    @GetMapping
    public List<TradingCard> getCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return tradingCardService.getCards(page, size);
    }


    // GET /api/cards/filter?minPrice=&maxPrice=&specialty=&sort=
    @GetMapping("/filter")
    public List<TradingCard> filterCards(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String sort
    ) {
        return tradingCardService.filterCards(minPrice, maxPrice, specialty, sort);
    }

    // GET /api/cards/search?query=somevalue
    @GetMapping("/search")
    public List<TradingCard> searchCards(
            @RequestParam String query
    ) {
        return tradingCardService.searchCards(query);
    }
}
