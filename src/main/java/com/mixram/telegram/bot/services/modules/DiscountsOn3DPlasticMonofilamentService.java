package com.mixram.telegram.bot.services.modules;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mixram.telegram.bot.services.domain.Data3DPlastic;
import com.mixram.telegram.bot.utils.databinding.JsonUtil;
import com.mixram.telegram.bot.utils.htmlparser.HtmlPageParser;
import com.mixram.telegram.bot.utils.htmlparser.ParseData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mixram on 2019-03-29.
 * @since 0.1.1.0
 */
@Log4j2
@Service
class DiscountsOn3DPlasticMonofilamentService extends DiscountsOn3DPlasticV2Service {

    // <editor-fold defaultstate="collapsed" desc="***API elements***">

    //

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="***Util elements***">

    @Autowired
    public DiscountsOn3DPlasticMonofilamentService(@Value("${parser.mono.urls}") String urls,
                                                   @Value("${parser.common.time-to-wait-till-parse-new-url}") long waitTime,
                                                   @Qualifier("htmlPageMonoParser") HtmlPageParser parser) {
        super(JsonUtil.fromJson(urls, new TypeReference<List<ParseData>>() {}), waitTime, parser);
    }

    // </editor-fold>


    /**
     * To search for discounts.
     *
     * @return data or exception.
     *
     * @since 0.1.0.0
     */
    @Override
    public Data3DPlastic search() {
        return super.search();
    }


    // <editor-fold defaultstate="collapsed" desc="***Private elements***">

    //

    // </editor-fold>
}