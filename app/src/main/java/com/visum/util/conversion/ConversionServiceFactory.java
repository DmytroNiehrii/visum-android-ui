package com.visum.util.conversion;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionServiceFactory {

    private static DefaultConversionService instance;

    public static ConversionService getInstance() {
        if (instance == null) {
            instance = new DefaultConversionService();
            instance.addConverter(new OutCommunityDtoToOutCommunityShortDtoConverter());
        }
        return instance;
    }
}
