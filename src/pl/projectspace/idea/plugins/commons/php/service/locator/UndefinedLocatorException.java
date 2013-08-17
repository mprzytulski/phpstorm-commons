package pl.projectspace.idea.plugins.commons.php.service.locator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class UndefinedLocatorException extends Exception {

    public UndefinedLocatorException(String message) {
        super(message);
    }

}
