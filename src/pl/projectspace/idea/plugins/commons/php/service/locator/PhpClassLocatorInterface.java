package pl.projectspace.idea.plugins.commons.php.service.locator;

import pl.projectspace.idea.plugins.commons.php.service.locator.exceptions.MissingElementException;

import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public interface PhpClassLocatorInterface {

    public List<Class> getSupportedTypes();

    public <T>T locate(String key) throws MissingElementException;

}
