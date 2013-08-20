package pl.projectspace.idea.plugins.commons.php.psi.exceptions;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class InvalidMethodArgumentsException extends InvalidMethodCallException {
    public InvalidMethodArgumentsException(String message) {
        super(message);
    }
}
