package pl.projectspace.idea.plugins.commons.php.psi.exceptions;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class InvalidArgumentException extends Exception {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
