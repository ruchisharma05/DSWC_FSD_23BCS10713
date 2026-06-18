package com.devtrack.tickets.jackson;

import com.devtrack.tickets.dto.BugReportDTO;
import com.devtrack.tickets.dto.FeatureRequestDTO;
import com.devtrack.tickets.exception.UnsupportedTicketTypeException;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

import java.io.IOException;
import java.util.Locale;

public class TicketTypeResolver extends TypeIdResolverBase {

    private JavaType baseType;

    @Override
    public void init(JavaType baseType) {
        this.baseType = baseType;
    }

    @Override
    public String idFromValue(Object value) {
        if (value instanceof BugReportDTO) {
            return "BUG";
        }
        if (value instanceof FeatureRequestDTO) {
            return "FEATURE";
        }
        return null;
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        if (BugReportDTO.class.equals(suggestedType)) {
            return "BUG";
        }
        if (FeatureRequestDTO.class.equals(suggestedType)) {
            return "FEATURE";
        }
        return idFromValue(value);
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) throws IOException {
        if (id == null) {
            throw new UnsupportedTicketTypeException("null");
        }

        String normalizedType = id.toUpperCase(Locale.ROOT);

        return switch (normalizedType) {
            case "BUG" -> context.constructSpecializedType(baseType, BugReportDTO.class);
            case "FEATURE" -> context.constructSpecializedType(baseType, FeatureRequestDTO.class);
            default -> throw new UnsupportedTicketTypeException(id);
        };
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}
