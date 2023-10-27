package dk.bondegaard.team.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class GsonUtil {

    private final static Gson gson = new GsonBuilder()
            .create();

    @NotNull
    public static JsonObject serialize(Object object) {
        return gson.toJsonTree(object).getAsJsonObject();
    }

    @NotNull
    public static <T> T deserialize(JsonElement jsonElement, Class<T> typeClass) {
        return gson.fromJson(jsonElement, typeClass);
    }

    @NotNull
    public static <T> T deserialize(JsonElement jsonElement, TypeToken<T> type) {
        return gson.fromJson(jsonElement, type.getType());
    }

}

