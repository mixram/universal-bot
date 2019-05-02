package com.mixram.telegram.bot.utils.htmlparser;

import com.mixram.telegram.bot.utils.htmlparser.v2.entity.ParseData;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mixram on 2019-04-09.
 * @since 0.1.1.0
 * @deprecated legacy since 1.4.2.0, use 'v2'.
 */
@Deprecated
@Log4j2
@Service
public class HtmlPage3DPlastParserV2 extends HtmlPageShopParser {

    // <editor-fold defaultstate="collapsed" desc="***API elements***">

    private final String productNameSelectorName;
    private final String productAvailableTextName;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="***Util elements***">

    @Autowired
    public HtmlPage3DPlastParserV2(
            @Value("${parser.3dplast.search-names.old-price-selector-name.name}") String oldPriceSelectorName,
            @Value("${parser.3dplast.search-names.new-price-selector-name.name}") String newPriceSelectorName,
            @Value("${parser.3dplast.search-names.product-name-selector-name.name}") String productNameSelectorName,
            @Value("${parser.3dplast.search-names.product-presence-selector-name.name}") String productAvailableSelectorName,
            @Value("${parser.3dplast.search-names.product-presence-pattern-name.name}") String productAvailableTextName) {
        super(oldPriceSelectorName, newPriceSelectorName, productAvailableSelectorName);
        this.productNameSelectorName = productNameSelectorName;
        this.productAvailableTextName = productAvailableTextName;
    }

    // </editor-fold>

    /**
     * @since 0.1.0.0
     */
    @Override
    public ParseData parse(ParseData parseData) {
        return super.parse(parseData);
    }


    // <editor-fold defaultstate="collapsed" desc="***Private elements***">

    /**
     * @since 0.1.0.0
     */
    @Override
    protected BigDecimal parsePrice(Elements elements) {
        String priceText = elements.first().text();
        String[] split = priceText.split(" ");
        String sumString = split[0].replace(",", ".");

        return new BigDecimal(sumString);
    }

    /**
     * @since 0.2.0.0
     */
    @Override
    protected String getProductName(Document doc) {
        Elements nameElements = doc.select(productNameSelectorName);

        return nameElements.isEmpty() ? null : nameElements.first().text();
    }

    /**
     * @since 1.1.0.0
     */
    @Override
    protected boolean checkPresence(Elements presenceElements) {
        Pattern pattern = Pattern.compile(productAvailableTextName.toUpperCase());
        Matcher matcher = pattern.matcher(presenceElements.first().text().trim().toUpperCase());

        return matcher.matches();
    }

    // </editor-fold>
}
