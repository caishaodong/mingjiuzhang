package com.dong.mingjiuzhang.global.config.format;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.math.BigDecimal;

@JsonComponent
public class BigDecimalDeSerializer extends JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        // 千分位分隔的数值从前端到后端是需要反序列化为BigDecimal。需要去掉“,”
        return new BigDecimal(jsonParser.getText().replaceAll(",", ""));
    }
}
