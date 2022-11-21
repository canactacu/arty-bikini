package ru.arty_bikini.crm.utils;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.arty_bikini.crm.Utils;

import java.io.IOException;
import java.time.LocalDateTime;


public class LocalDateTimeToLongSerializer extends JsonSerializer<LocalDateTime> {
    
    @Override
    public void serialize(LocalDateTime time, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeNumber(Utils.toLongFromTime(time));
    }
    
}
