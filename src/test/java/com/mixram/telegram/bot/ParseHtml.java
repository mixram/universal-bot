package com.mixram.telegram.bot;

import com.mixram.telegram.bot.services.domain.enums.PlasticType;
import com.mixram.telegram.bot.utils.databinding.JsonUtil;
import com.mixram.telegram.bot.utils.htmlparser.ParseData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mixram on 2019-04-26.
 * @since 1.4.1.0
 */
public class ParseHtml {

    public static void main(String[] args) throws IOException {
        final String url = "https://cs2734145.prom.ua/p915909169-utsenka-nylon-175.html";

        Document doc = Jsoup.connect(url)
                            .header("accept",
                                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            .header("accept-encoding", "gzip, deflate, br")
                            .header("accept-language", "en-US,en;q=0.9")
                            .header("cache-control", "max-age=0")
                            .header("user-agent",
                                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36")
                            .get();

        ParseData data = new ParseData();
        data.setType(PlasticType.NYLON);
        data.setPageTitle(doc.title());
        data.setProductUrl(url);

        Elements oldPriceElements = doc.select("[class=\"b-product-cost__old-price\"]");
        if (!oldPriceElements.isEmpty()) {
            data.setProductOldPrice(parsePrice(oldPriceElements));
        }

        Elements salePriceElements = doc.select("[class=\"b-product-cost__price\"]");
        if (!salePriceElements.isEmpty()) {
            data.setProductSalePrice(parsePrice(salePriceElements));
        }

        data.setProductName(getProductName(doc));

        Elements presenceElements = doc.select("[data-qaid=\"presence_data\"]");
        if (!presenceElements.isEmpty()) {
            data.setInStock(checkPresence(presenceElements));
        }

        System.out.println("PARSED: " + JsonUtil.toPrettyJson(data));
    }

    private static BigDecimal parsePrice(Elements elements) {
        String priceText = elements.first().text();
        String[] split = priceText.split(" ");
        String sumString = split[0].replace(",", ".");

        return new BigDecimal(sumString);
    }

    private static String getProductName(Document doc) {
        Elements nameElements = doc.select("[data-qaid=\"product_name\"]");

        return nameElements.isEmpty() ? null : nameElements.first().text();
    }

    private static boolean checkPresence(Elements presenceElements) {
        Pattern pattern = Pattern.compile(
                "\u0412 \u043D\u0430\u043B\u0438\u0447\u0438\u0438|\u0417\u0430\u043A\u0430\u043D\u0447\u0438\u0432\u0430\u0435\u0442\u0441\u044F".toUpperCase());
        Matcher matcher = pattern.matcher(presenceElements.first().text().trim().toUpperCase());

        return matcher.matches();
    }

}