package pl.projectspace.idea.plugins.commons.php.code.resolver;

import com.intellij.psi.PsiElement;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidMethodArgumentsException;
import pl.projectspace.idea.plugins.commons.php.service.locator.exceptions.MissingElementException;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public interface NamedObjectResolverInterface {

    public <T>T resolve(PsiElement element) throws InvalidMethodArgumentsException, MissingElementException;

}
