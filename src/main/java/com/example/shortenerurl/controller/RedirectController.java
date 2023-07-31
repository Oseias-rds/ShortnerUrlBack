package com.example.shortenerurl.controller;

import com.example.shortenerurl.dto.ShortUrlDTO;
import com.example.shortenerurl.dto.StatisticsUrlDTO;
import com.example.shortenerurl.service.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("/")
public class RedirectController {

    @Autowired
    private ShortenerService shortenerService;

    @GetMapping("/{shortUrl}")
    public void shortToLong(@PathVariable String shortUrl, HttpServletResponse servletResponse) throws IOException {

        // Incrementa o contador para a URL curta no HashMap
        String longUrl = shortenerService.shortToLong(shortUrl);
        if(longUrl==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url não encontrada");

        }

        servletResponse.setHeader("Location", longUrl);
        servletResponse.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
    }

    @GetMapping("/shortner")
    public ResponseEntity<?> shortenUrl(@RequestParam("url") String longUrl) {
        // Verificar se a URL é válida
        if (!isValidUrl(longUrl)) {
            return ResponseEntity.badRequest().body("{\"error\": \"URL inválida\"}");
        }
        String shortUrl = shortenerService.longToShort(longUrl);
        ShortUrlDTO shortUrlDTO = new ShortUrlDTO();
        shortUrlDTO.setShortUrl(shortUrl);
        return ResponseEntity.ok(shortUrlDTO);
    }
    private boolean isValidUrl(String longUrl) {
        try {
            new URL(longUrl);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    @GetMapping("/getCount")
    public StatisticsUrlDTO getCount(@RequestParam("shortUrl") String shortUrl) {
        // Recupera o valor do contador para a URL curta no HashMap
        Integer response = shortenerService.getCount(shortUrl);
        if(response==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url não encontrada");

        }
        StatisticsUrlDTO statisticsUrlDTO = new StatisticsUrlDTO();
        statisticsUrlDTO.setUrlAccessCount(response);
        return  statisticsUrlDTO;

    }

}
