package edu.byui.apj.storefront.web.controller;

import edu.byui.apj.storefront.web.model.TradingCard;
import edu.byui.apj.storefront.web.service.TradingCardClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "http://localhost:8181")
public class TradingCardController {

    private final TradingCardClientService tradingCardService;

    @Autowired
    public TradingCardController(TradingCardClientService tradingCardService) {
        this.tradingCardService = tradingCardService;
    }


    // GET /api/cards?page=0&size=20
    @GetMapping
    public List<TradingCard> getCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return tradingCardService.getAllCardsPaginated(page, size);
    }


    // GET /api/cards/filter?minPrice=&maxPrice=&specialty=&sort=
    @GetMapping("/filter")
    public List<TradingCard> filterCards(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String sort
    ) {
        return tradingCardService.filterAndSort(minPrice, maxPrice, specialty, sort);
    }

    // GET /api/cards/search?query=somevalue
    @GetMapping("/search")
    public List<TradingCard> searchCards(
            @RequestParam String query
    ) {
        return tradingCardService.searchByNameOrContribution(query);
    }
}
