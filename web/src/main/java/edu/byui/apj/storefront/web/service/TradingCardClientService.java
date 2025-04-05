package edu.byui.apj.storefront.web.service;

import edu.byui.apj.storefront.web.model.TradingCard;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;
import java.util.List;

@Service
public class TradingCardClientService {

    private final WebClient webClient;

    // Constructor injection for WebClient
    public TradingCardClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/api/cards").build(); // URL of the API
    }

    // Returns the list of cards starting at position page * size and returning size elements.
    public List<TradingCard> getAllCardsPaginated(int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("page", page).queryParam("size", size).build())
                .retrieve()
                .bodyToFlux(TradingCard.class)
                .collectList()
                .block();
    }

    // Returns the list resulting from filtering by minPrice, maxPrice, or specialty, then sorting by sort.
    public List<TradingCard> filterAndSort(BigDecimal minPrice, BigDecimal maxPrice, String specialty, String sort) {
        String url = "/filter";
        StringBuilder uri = new StringBuilder(url);

        if (minPrice != null || maxPrice != null || specialty != null || sort != null) {
            uri.append("?");
            if (minPrice != null) uri.append("minPrice=").append(minPrice).append("&");
            if (maxPrice != null) uri.append("maxPrice=").append(maxPrice).append("&");
            if (specialty != null) uri.append("specialty=").append(specialty).append("&");
            if (sort != null) uri.append("sort=").append(sort).append("&");

            uri.deleteCharAt(uri.length() - 1); // Remove trailing '&'
        }

        return webClient.get()
                .uri(uri.toString())
                .retrieve()
                .bodyToFlux(TradingCard.class)
                .collectList()
                .block();
    }

    // Returns the list of cards resulting from the query string (case insensitive) found in the name or contribution.
    public List<TradingCard> searchByNameOrContribution(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToFlux(TradingCard.class)
                .collectList()
                .block();
    }


}
