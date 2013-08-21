package pl.projectspace.idea.plugins.commons.php.code.locator;

import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public interface ObjectLocatorInterface {

    public <T>T locate(String name) throws MissingElementException;

}
