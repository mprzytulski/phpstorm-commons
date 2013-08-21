package pl.projectspace.idea.plugins.commons.php.code.type;

import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.commons.php.psi.element.MethodDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class GenericMethodCallTypeProvider implements PhpTypeProvider2 {

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

        try {
            MethodDecorator method = (MethodDecorator) getMethod((MethodReference) element);

            return method.getReturnType().toString();
        } catch (InvalidArgumentException e) {
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

    protected abstract Object getMethod(MethodReference method) throws InvalidArgumentException, MissingElementException;
}
