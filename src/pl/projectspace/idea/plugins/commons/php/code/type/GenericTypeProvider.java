package pl.projectspace.idea.plugins.commons.php.code.type;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;

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

}
