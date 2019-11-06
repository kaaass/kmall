package net.kaaass.kmall.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.kaaass.kmall.vo.PromoteStyle;

import java.io.IOException;

public class PromoteStyleSerializer extends JsonSerializer<PromoteStyle> {
    @Override
    public void serialize(PromoteStyle promoteStyle, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(promoteStyle.getStyle());
    }
}
