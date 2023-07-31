package com.example.shortenerurl.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import com.example.shortenerurl.controller.ShortenerController;

import org.springframework.stereotype.Service;

@Service
public class ShortenerService {

    private HashMap<String, String> urlMap = new HashMap<>();
    Map<String, Integer> counters = new HashMap<>();

    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    public String longToShort(String longUrl) {


        if(longUrl.trim().equals("")) {
            return null;
        }

        // Criando uma string vazia para armazenar o resultado
        StringBuilder shortUrlBuilder = new StringBuilder();

        // Gerando a sequência de caracteres do short URL

        Random random = new Random(System.nanoTime());

        
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            shortUrlBuilder.append(randomChar);
        }

        // Convertendo o StringBuilder para String final
        String shortUrl = shortUrlBuilder.toString();

        // Armazenando a relação entre a URL curta (shortUrl) e a URL longa (longUrl) no mapa
        urlMap.put(shortUrl, longUrl);

        counters.put(shortUrl, 0);


        return shortUrl;
    }


    public String shortToLong(String shortUrl) {

        String response = urlMap.get(shortUrl);

        if(response != null) {

            counters.put(shortUrl, counters.get(shortUrl) + 1);
        }
        return response;
    }

    public Integer getCount(String shortUrl){
        return counters.get(shortUrl);
    }
}
