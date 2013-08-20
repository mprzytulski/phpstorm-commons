package pl.projectspace.idea.plugins.commons.php.code.resolver;

import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.commons.php.code.locator.ObjectLocatorInterface;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidMethodArgumentsException;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
abstract public class GenericObjectResolver implements NamedObjectResolverInterface {

    protected ObjectLocatorInterface locator;

    public GenericObjectResolver(ObjectLocatorInterface locator) {
        this.locator = locator;
    }

    public StringLiteralExpression getPointer(MethodReference methodReference) {
        return ((StringLiteralExpression) methodReference.getParameters()[0]);
    }

    private String getName(MethodReference methodReference) throws InvalidMethodArgumentsException {
        return getName(getPointer(methodReference));
    }

    protected String getName(StringLiteralExpression name) {
        return name.getContents();
    }

}
