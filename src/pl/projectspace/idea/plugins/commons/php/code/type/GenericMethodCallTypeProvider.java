package pl.projectspace.idea.plugins.commons.php.code.type;

import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.xml.ui.PsiTypePanel;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.commons.php.code.resolver.NamedObjectResolverInterface;
import pl.projectspace.idea.plugins.commons.php.code.validator.MethodCallValidatorInterface;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidMethodArgumentsException;
import pl.projectspace.idea.plugins.commons.php.service.locator.exceptions.MissingElementException;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GenericMethodCallTypeProvider implements PhpTypeProvider2 {

    protected MethodCallValidatorInterface validator;
    protected NamedObjectResolverInterface resolver;

    public GenericMethodCallTypeProvider(MethodCallValidatorInterface validator, NamedObjectResolverInterface resolver) {
        this.validator = validator;
        this.resolver = resolver;
    }

    @Override
    public char getKey() {
        return 192;
    }

    @Nullable
    @Override
    public String getType(PsiElement element) {
        if (DumbService.isDumb(element.getProject()) || !(element instanceof MethodReference)) {
            return null;
        }

        MethodReference methodReference = (MethodReference) element;

        if (!validator.isValidDeclaration(methodReference)) {
            return null;
        }

        try {
            return resolver.resolve(methodReference);
        } catch (InvalidMethodArgumentsException e) {
            return null;
        } catch (MissingElementException e) {
            return null;
        }
    }

    /**
     * Return context instance for given expression - FQN
     *
     * @param expression
     * @param project
     * @return
     */
    @Override
    public Collection<? extends PhpNamedElement> getBySignature(String expression, Project project) {
        PhpIndex phpIndex = PhpIndex.getInstance(project);
        PhpClass phpClass = phpIndex.getClassesByFQN(expression).iterator().next();

        return Arrays.asList(phpClass);
    }
}
