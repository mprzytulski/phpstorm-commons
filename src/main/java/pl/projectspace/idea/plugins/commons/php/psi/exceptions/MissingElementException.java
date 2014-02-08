package pl.projectspace.idea.plugins.commons.php.psi.exceptions;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class MissingElementException extends Exception {
    public MissingElementException(String message) {
        super(message);
    }
}
