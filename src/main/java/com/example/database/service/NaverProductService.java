package com.example.database.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class NaverProductService {

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    public String fetchProductDataFromNaver(String query) {
        String apiURL = "https://openapi.naver.com/v1/search/shop.json?query=" + query;

        try {
            // URL 객체 생성
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET"); // GET 메서드 설정
            con.setRequestProperty("X-Naver-Client-Id", clientId); // 클라이언트 ID
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret); // 클라이언트 Secret

            int responseCode = con.getResponseCode(); // 응답 코드 확인
            if (responseCode == 200) { // 성공
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                return response.toString();
            } else { // 실패
                return "Error: API Response Code " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to fetch data from Naver API";
        }

    }
}
