package pl.projectspace.idea.plugins.commons.php.psi.exceptions;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class FailedToLocateContainingClassException extends Exception {
    public FailedToLocateContainingClassException(String message) {
        super(message);
    }
}
