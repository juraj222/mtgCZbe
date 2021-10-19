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
        List<CRCard> mockCards = new ArrayList<>();
        CRCard card1 = new CRCard(1, "test", 0);
        CRCard card2 = new CRCard(1, "test", 0);

        mockCards.add(card1);
        mockCards.add(card2);
        return mockCards;
    }

    private static int findPrice(String cardName) {
        //https://htmlunit.sourceforge.io/
        int lowest = 999999;
        boolean noStock = true;

        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            final HtmlPage page = webClient.getPage("http://cernyrytir.cz/index.php3?akce=3");
            final HtmlForm form = page.getFormByName("kusovkymagic");
            final HtmlTextInput jmenokarty = form.getInputByName("jmenokarty");
            final HtmlSubmitInput submitbutton = form.getInputByName("submit");

            jmenokarty.type(cardName);
            final HtmlPage searchResults = submitbutton.click();

            List<DomElement> names = searchResults.getByXPath("//font");

            int i = 0; //name 1 - stock 2 - price 3
            boolean isStocked = false;
            boolean correctName = false;
            for(DomElement el :names) {
                i++;
                if(i == 1) {
                    if (el.getVisibleText().toLowerCase().replaceAll("[-+.^:,'´]","").equals(cardName.toLowerCase().replaceAll("[-+.^:,'´]",""))) {
                        correctName = true;
                    } else if (el.getVisibleText().toLowerCase().replaceAll("[-+.^:,'´]","").equals(cardName + " - foil".toLowerCase().replaceAll("[-+.^:,'´]",""))) {
                        correctName = true;
                    }
                }
                if(i == 2) {
                    int stock = Integer.parseInt(el.getVisibleText().replaceAll("[\\D]", ""));
                    if(stock > 0 && correctName) {
                        isStocked = true;
                        noStock = false;
                    }
                }
                if(i == 3) {
                    if(isStocked && correctName) {
                        int price = Integer.parseInt(el.getVisibleText().replaceAll("[\\D]", ""));
                        if(price < lowest)
                            lowest = price;
                    }
                    i = 0;
                    isStocked = false;
                    correctName = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (noStock) {
            return 0;
        } else {
            return lowest;
        }
    }
}
