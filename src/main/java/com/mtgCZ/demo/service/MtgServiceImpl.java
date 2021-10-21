package com.mtgCZ.demo.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.mtgCZ.demo.model.CRCard;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MtgServiceImpl implements MtgService{

    @Override
    public List<CRCard> findCard(String cardName) {
        return findCardOnCR(cardName);
    }

    private static List<CRCard> findCardOnCR(String cardName) {
        //https://htmlunit.sourceforge.io/
        List<CRCard> findCards = new ArrayList<>();
        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            final HtmlPage page = webClient.getPage("http://cernyrytir.cz/index.php3?akce=3");
            final HtmlForm form = page.getFormByName("kusovkymagic");
            final HtmlTextInput jmenokarty = form.getInputByName("jmenokarty");
            final HtmlSubmitInput submitbutton = form.getInputByName("submit");

            jmenokarty.type(cardName);
            final HtmlPage searchResults = submitbutton.click();

            List<DomElement> names = searchResults.getByXPath("//font");

            int i = 0; //name 1 - stock 2 - price 3
            CRCard actualCard = null;
            for(DomElement el :names) {
                i++;
                if(i == 1) {
                    actualCard = new CRCard();
                    actualCard.setName(el.getVisibleText());
                }
                if(i == 2 && actualCard != null) {
                    actualCard.setStock(Integer.parseInt(el.getVisibleText().replaceAll("[\\D]", "")));
                }
                if(i == 3 && actualCard != null) {
                    actualCard.setPrice(Integer.parseInt(el.getVisibleText().replaceAll("[\\D]", "")));
                    findCards.add(actualCard);
                    i = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return findCards;
    }
}
