package pl.projectspace.idea.plugins.commons.php.composer.converter;

import com.google.gson.*;
import pl.projectspace.idea.plugins.commons.php.composer.Configuration;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ConfigurationConverter implements JsonDeserializer<Configuration> {
    @Override
    public Configuration deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Configuration config = new Configuration();

        for (Map.Entry<String, JsonElement>element : jsonElement.getAsJsonObject().entrySet()) {
            config.set(element.getKey(), element.getValue().getAsString());
        }

        return config;
    }
}
