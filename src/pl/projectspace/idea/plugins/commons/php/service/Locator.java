package pl.projectspace.idea.plugins.commons.php.service;

import pl.projectspace.idea.plugins.commons.php.service.locator.PhpClassLocatorInterface;
import pl.projectspace.idea.plugins.commons.php.service.locator.UndefinedLocatorException;

import java.util.HashMap;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class Locator {

    protected HashMap<Class, PhpClassLocatorInterface> locators;

    public void Locator() {

    }

    public void registryLocator(PhpClassLocatorInterface locator) {
        for (Class supported : locator.getSupportedTypes()) {
            locators.put(supported, locator);
        }
    }

    public PhpClassLocatorInterface getLocatorFor(Class type) throws UndefinedLocatorException {
        if (!locators.containsKey(type)) {
            throw new UndefinedLocatorException("Failed to find locator for type: "+type.toString());
        }
        return locators.get(type);
    }

    public <T> T locate(String key, Class<T> type) throws UndefinedLocatorException {
        if (!locators.containsKey(type)) {
            throw new UndefinedLocatorException("Undefined locator for class type: "+type.toString());
        }

        Object t = "0";
        return (T)t;
    }

}
