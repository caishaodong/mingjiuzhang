package com.dong.mingjiuzhang.global.config.format;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@JsonComponent
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {
    private static final String FORMAT_1 = "#.00";
    private static final String FORMAT_2 = "0.00";

    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(new DecimalFormat((bigDecimal.compareTo(BigDecimal.ZERO) >= 0 && bigDecimal.compareTo(BigDecimal.ONE) < 1) ? FORMAT_2 : FORMAT_1)
                .format(bigDecimal));
    }
}
