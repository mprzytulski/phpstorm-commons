package pl.projectspace.idea.plugins.commons.php.psi.exceptions;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class InvalidMethodCallException extends Exception {
    public InvalidMethodCallException(String message) {
        super(message);
    }
}
