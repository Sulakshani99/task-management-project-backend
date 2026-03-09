package lk.taskmanager.api.config.security.custom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeBooleanField("success", false);
        gen.writeStringField("message", value.getMessage());
        gen.writeNumberField("status", value.getHttpErrorCode());
        gen.writeEndObject();
    }
}
