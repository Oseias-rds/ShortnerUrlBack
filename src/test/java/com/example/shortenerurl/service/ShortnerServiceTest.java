package com.example.shortenerurl.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ShortnerServiceTest {

    @Autowired
    private ShortenerService shortenerService;


    @Test
    @DisplayName("Prrecisa retonar uma URL/texto de seis(6) caracteres")
    public void shouldReturnShortString(){
        String longUrl = "https://www.youtube.com/watch?v=eQakqjYKk94";
        String shortUrl = shortenerService.longToShort(longUrl);

        assertEquals(shortUrl.length(), 6);
    }

    @Test
    @DisplayName("Prrecisa retonar null caso a longUrl for vazia")
    public void shouldReturnShortNull(){
        String lonUrl = "      ";
        String shortUrl = shortenerService.longToShort(lonUrl);

        assertNull(shortUrl);


    }
    @Test
    @DisplayName("logsURLs iguais geram shortUrls diferentes")
    public void DifferentLongUrls() {
        String longUrl = "https://www.youtube.com/watch?v=eQakqjYKk94";
        String shortUrl1 = shortenerService.longToShort(longUrl);
        String shortUrl2 = shortenerService.longToShort(longUrl);

        assertNotEquals(shortUrl1, shortUrl2);
    }

    @Test
    @DisplayName("Recuperar URL longa para URL curta existente")
    public void LongUrlForShortUrl() {
        String longUrl = "https://www.youtube.com/watch?v=eQakqjYKk94";
        String shortUrl = shortenerService.longToShort(longUrl);

        String retrievedLongUrl = shortenerService.shortToLong(shortUrl);

        assertEquals(longUrl, retrievedLongUrl);
    }

    @Test
    @DisplayName("URL curta inexistente retorna null")
    public void nonExistentShortUrlReturnsNull() {
        String longUrl = "https://www.youtube.com/watch?v=eQakqjYKk94";

        String retrievedLongUrl = shortenerService.shortToLong(longUrl);

        assertNull(retrievedLongUrl);
    }

    @Test
    @DisplayName("Contagem de acessos para URL curta não acessada")
    public void NonExistentShortUrl() {
        String longUrl = "https://www.youtube.com/watch?v=eQakqjYKk94";
        String shortUrl = shortenerService.longToShort(longUrl);

        Integer accessCount = shortenerService.getCount(shortUrl);

        assertEquals(0, accessCount);
    }




}
