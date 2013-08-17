package pl.projectspace.idea.plugins.commons.php.service.locator;

import com.jetbrains.php.lang.psi.elements.PhpClass;

import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public interface PhpClassLocatorInterface {

    public List<Class> getSupportedTypes();

    public <T>T locate(String key);

}
