package com.nakuring.enhanced_boss_bars.bars_loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;

public class IdentifierAdapter
        implements JsonDeserializer<ResourceLocation>,
        JsonSerializer<ResourceLocation> {

    @Override
    public ResourceLocation deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context
    ) {

        return ResourceLocation.parse(
                json.getAsString()
        );
    }

    @Override
    public JsonElement serialize(
            ResourceLocation src,
            Type typeOfSrc,
            JsonSerializationContext context
    ) {

        return new JsonPrimitive(
                src.toString()
        );
    }
}