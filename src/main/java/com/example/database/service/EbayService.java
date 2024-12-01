package com.example.database.service;

import com.example.database.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.database.entity.Product;
import com.example.database.repository.ProductRepository;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EbayService {

    private static final Logger logger = LoggerFactory.getLogger(EbayService.class);

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public EbayService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.objectMapper = new ObjectMapper();
    }

    @Value("${ebay.api.url}")
    private String ebayApiUrl;

    @Value("${exchange.api.key}")
    private String authKey;

    @Value("${exchange.api.url}")
    private String exchangeApiUrl;

    public String getProductInfo(String accessToken, String searchQuery) {
        RestTemplate restTemplate = new RestTemplate();

        String encodedSearchQuery = null;
        try {
            encodedSearchQuery = URLEncoder.encode(searchQuery, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error encoding search query: {}", e.getMessage());
            return "Error encoding search query";
        }

        String apiUrl = ebayApiUrl + "/buy/browse/v1/item_summary/search?q=" + encodedSearchQuery;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
            String jsonResponse = response.getBody();
            saveEbayProducts(jsonResponse);
            return jsonResponse;
        } catch (Exception e) {
            logger.error("Error retrieving product info from eBay: {}", e.getMessage());
            return "Error retrieving product info";
        }
    }

    private BigDecimal fetchUSDToKRWExchangeRate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance(); // 현재 날짜 가져오기

        for (int i = 0; i < 7; i++) { // 최대 7일간 시도
            String searchDate = dateFormat.format(calendar.getTime()); // 현재 날짜 포맷팅
            String url = exchangeApiUrl + "?authkey=" + authKey + "&searchdate=" + searchDate + "&data=AP01";

            try {
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                for (JsonNode node : rootNode) {
                    if ("USD".equals(node.path("cur_unit").asText())) {
                        String dealBasRate = node.path("deal_bas_r").asText().replace(",", "");
                        logger.info("Exchange rate for {} (USD to KRW): {}", searchDate, dealBasRate);
                        return new BigDecimal(dealBasRate); // 유효한 환율 반환
                    }
                }
            } catch (Exception e) {
                logger.warn("Failed to fetch exchange rate for date {}: {}", searchDate, e.getMessage());
            }

            // 하루 전으로 날짜 이동
            calendar.add(Calendar.DATE, -1);
        }

        logger.error("Failed to fetch exchange rate for the last 7 days. Defaulting to rate 1.");
        return BigDecimal.ONE; // 기본 환율 반환
    }

    public void saveEbayProducts(String jsonResponse) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode itemSummariesNode = rootNode.path("itemSummaries");

            BigDecimal exchangeRate = fetchUSDToKRWExchangeRate(); // 실시간 환율 가져오기

            if (exchangeRate == null || exchangeRate.compareTo(BigDecimal.ZERO) == 0) {
                logger.error("Failed to fetch valid exchange rate. Using default rate of 1.");
                exchangeRate = BigDecimal.ONE;
            }

            List<Product> products = new ArrayList<>();

            for (JsonNode itemNode : itemSummariesNode) {
                Product product = new Product();
                product.setTitle(itemNode.path("title").asText());
                product.setLink(itemNode.path("itemWebUrl").asText());

                String currency = itemNode.path("price").path("currency").asText();
                product.setCurrency(currency);
                product.setHprice(itemNode.path("price").path("value").asInt()); // 상품 가격
                BigDecimal price = new BigDecimal(itemNode.path("price").path("value").asText());

                if ("USD".equals(currency)) {
                    price = price.multiply(exchangeRate); // USD -> KRW 변환
                }

                product.setLprice(price.intValue());

                JsonNode imageNode = itemNode.path("image");
                String imageUrl = imageNode.path("imageUrl").asText();
                product.setImage(imageUrl);

                product.setProductId(itemNode.path("seller").path("username").asText());
                product.setMallName("이베이");



                JsonNode categoriesNode = itemNode.path("categories");
                String category1 = categoriesNode.size() > 0 ? categoriesNode.get(0).path("categoryName").asText() : null;
                String category2 = categoriesNode.size() > 1 ? categoriesNode.get(1).path("categoryName").asText() : null;
                String category3 = categoriesNode.size() > 2 ? categoriesNode.get(2).path("categoryName").asText() : null;

                product.setCategory1(category1);
                product.setCategory2(category2);
                product.setCategory3(category3);

                products.add(product);
            }

            productRepository.saveAll(products);

        } catch (Exception e) {
            logger.error("Error saving eBay products to DB: {}", e.getMessage());
        }
    }
}
