package ru.arty_bikini.crm.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.arty_bikini.crm.Utils;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateToLongSerializer extends JsonSerializer<LocalDate> {
    
    @Override
    public void serialize(LocalDate time, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeNumber(Utils.toLong(time));
    }
    
}
