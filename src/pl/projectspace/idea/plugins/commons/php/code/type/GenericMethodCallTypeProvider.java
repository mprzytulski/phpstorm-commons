package pl.projectspace.idea.plugins.commons.php.code.type;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.psi.element.MethodDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.element.PhpClassDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.element.PsiElementDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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

            if (!(((PsiElementDecorator) method.getReturnType()).getDecoratedObject() instanceof PhpClass)) {
                return null;
            }

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
        PhpClass phpClass = ServiceManager.getService(project, PsiTreeUtils.class).getClassByFQN(expression);

        if (phpClass == null) {
            return Collections.emptyList();
        }

        return Arrays.asList(phpClass);
    }

    protected abstract Object getMethod(MethodReference method) throws InvalidArgumentException, MissingElementException;
}
