package pl.projectspace.idea.plugins.commons.php.code.type;

import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.commons.php.StateComponentInterface;
import pl.projectspace.idea.plugins.commons.php.code.inspection.DummyPhpElementVisitor;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.DependsOnPlugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class GenericTypeProvider implements PhpTypeProvider2 {

    @Override
    public char getKey() {
        return 192;
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


    @Nullable
    @Override
    public String getType(PsiElement element) {

        return getTypeFor(element);
    }

    protected boolean isEnabled(PsiElement element) {
        DependsOnPlugin annotation = this.getClass().getAnnotation(DependsOnPlugin.class);

        BaseComponent component = element.getProject().getComponent(annotation.value());
        if (component instanceof StateComponentInterface) {
            return ((StateComponentInterface) component).isEnabled();
        }

        return false;
    }

    protected abstract String getTypeFor(PsiElement psiElement);

}
