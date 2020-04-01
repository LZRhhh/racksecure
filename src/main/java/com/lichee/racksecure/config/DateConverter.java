package com.lichee.racksecure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import java.sql.Timestamp;



public class DateConverter implements Converter<String, Timestamp> {
    @Override
    public Timestamp convert(String s) {
        if ("".equals(s) || null == s) {
            return null;
        }

        try {
            return Timestamp.valueOf(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
