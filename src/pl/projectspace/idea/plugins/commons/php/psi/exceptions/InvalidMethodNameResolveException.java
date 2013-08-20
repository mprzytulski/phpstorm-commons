package pl.projectspace.idea.plugins.commons.php.psi.exceptions;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class InvalidMethodNameResolveException extends InvalidMethodCallException {
    public InvalidMethodNameResolveException(String message) {
        super(message);
    }
}
