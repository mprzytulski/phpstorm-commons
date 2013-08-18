package pl.projectspace.idea.plugins.commons.php.psi.exceptions;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PhpClassNotFoundException extends Exception {
    public PhpClassNotFoundException(String message) {
        super(message);
    }
}
