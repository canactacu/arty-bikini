package ru.arty_bikini.crm.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.arty_bikini.crm.Utils;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateToLongDeserializer extends JsonDeserializer<LocalDate> {
    
    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return Utils.toDate(parser.getNumberValue().intValue());
    }
}
