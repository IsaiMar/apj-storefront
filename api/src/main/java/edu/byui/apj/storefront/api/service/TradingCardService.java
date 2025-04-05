package edu.byui.apj.storefront.api.service;

import edu.byui.apj.storefront.api.model.TradingCard;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TradingCardService {

    private final List<TradingCard> cards = new ArrayList<>();

    // Load the CSV data into the list of TradingCards
    public TradingCardService() {
        loadTradingCardsFromCSV();
    }
    //ID,Name,Specialty,Contribution,Price,ImageUrl
    private void loadTradingCardsFromCSV() {
        try (
                InputStream is = getClass().getClassLoader().getResourceAsStream("pioneers.csv");
                InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(is));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())
        ) {
            for (CSVRecord record : csvParser) {
                Long id = Long.parseLong(record.get("ID"));
                String name = record.get("Name");
                String specialty = record.get("Specialty");
                String contribution = record.get("Contribution");
                BigDecimal price = new BigDecimal(record.get("Price"));
                String imageUrl = record.get("ImageUrl");

                cards.add(new TradingCard(id, name, specialty, contribution, price, imageUrl));
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace(); // Or use a logger for cleaner output
        }
    }

    // Method to return a page of results based on page and size
    public List<TradingCard> getCards(int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, cards.size());
        if (start >= cards.size()) return Collections.emptyList();
        return cards.subList(start, end);
    }

    // Method to filter and sort the list of cards based on parameters
    public List<TradingCard> filterCards(BigDecimal minPrice, BigDecimal maxPrice, String specialty, String sort) {
        return cards.stream()
                .filter(card -> (minPrice == null || card.getPrice().compareTo(minPrice) >= 0))
                .filter(card -> (maxPrice == null || card.getPrice().compareTo(maxPrice) <= 0))
                .filter(card -> (specialty == null || card.getSpecialty().equalsIgnoreCase(specialty)))
                .sorted((a, b) -> {
                    if ("price".equalsIgnoreCase(sort)) {
                        return a.getPrice().compareTo(b.getPrice());
                    } else if ("name".equalsIgnoreCase(sort)) {
                        return a.getName().compareToIgnoreCase(b.getName());
                    }
                    return 0;
                })
                .collect(Collectors.toList());
    }

    // Method to search for cards based on the query (name or contribution)
    public List<TradingCard> searchCards(String query) {
        String lowerQuery = query.toLowerCase();
        return cards.stream()
                .filter(card -> card.getName().toLowerCase().contains(lowerQuery)
                        || card.getContribution().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

}

