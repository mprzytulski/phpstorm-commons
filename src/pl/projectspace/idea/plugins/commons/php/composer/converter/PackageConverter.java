package pl.projectspace.idea.plugins.commons.php.composer.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PackageConverter implements JsonDeserializer<pl.projectspace.idea.plugins.commons.php.composer.Package> {
    @Override
    public pl.projectspace.idea.plugins.commons.php.composer.Package deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new pl.projectspace.idea.plugins.commons.php.composer.Package();
    }
}
