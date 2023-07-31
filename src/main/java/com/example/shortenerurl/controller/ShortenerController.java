package com.example.shortenerurl.controller;

import com.example.shortenerurl.service.ShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/shortner")
public class ShortenerController {
    @Autowired
    private ShortenerService shortenerService;



    @GetMapping
    public  String shortenUrl(@RequestParam("url") String longUrl) {
        return shortenerService.longToShort(longUrl);
    }

    @GetMapping("/getLong")
    public String shortToLong(@RequestParam("shortUrl") String shortUrl) {

        // Incrementa o contador para a URL curta no HashMap
        String response = shortenerService.shortToLong(shortUrl);
        if(response==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url não encontrada");

        }
        return response;

    }

    @GetMapping("/getCount")
    public Integer getCount(@RequestParam("shortUrl") String shortUrl) {
        // Recupera o valor do contador para a URL curta no HashMap
        Integer response = shortenerService.getCount(shortUrl);
        if(response==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url não encontrada");

        }
        return response;
    }


}

