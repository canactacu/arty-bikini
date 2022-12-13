package ru.arty_bikini.crm.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.arty_bikini.crm.Utils;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeToLongDeserializer extends JsonDeserializer<LocalDateTime> {
    
    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return Utils.toTime(parser.getNumberValue().longValue());
    }
}
