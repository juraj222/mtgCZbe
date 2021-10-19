package com.mtgCZ.demo.service;

import com.mtgCZ.demo.model.CRCard;

import java.util.List;

public interface MtgService {
    List<CRCard> findCard(String cardName);
}
