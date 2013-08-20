package pl.projectspace.idea.plugins.commons.php.code.validator;

import com.jetbrains.php.lang.psi.elements.MethodReference;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public interface MethodCallValidatorInterface {
    public boolean isValidDeclaration(MethodReference methodReference);
}
