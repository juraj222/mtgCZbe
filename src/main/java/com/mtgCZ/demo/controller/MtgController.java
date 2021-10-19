package com.mtgCZ.demo.controller;

import com.mtgCZ.demo.model.CRCard;
import com.mtgCZ.demo.service.MtgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/mtg")
public class MtgController {
    @Autowired
    private MtgService mtgService;

    @GetMapping(value="/{cardName}")
    public List<CRCard> findCardsOnMarket(@PathVariable String cardName) {
        return mtgService.findCard(cardName);
    }
}
