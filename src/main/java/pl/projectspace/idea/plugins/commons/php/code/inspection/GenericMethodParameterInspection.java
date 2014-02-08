package pl.projectspace.idea.plugins.commons.php.code.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.StateComponentInterface;
import pl.projectspace.idea.plugins.commons.php.psi.element.MethodDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.DependsOnPlugin;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.RequireMethod;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class GenericMethodParameterInspection extends LocalInspectionTool {

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new MethodParameterVisitor(holder);
    }

    public final class MethodParameterVisitor extends PhpElementVisitor {

        private ProblemsHolder holder;

        public MethodParameterVisitor(ProblemsHolder holder) {
            this.holder = holder;
        }

        @Override
        public void visitPhpMethodReference(MethodReference methodReference) {

            if (!isEnabled(methodReference)) {
                return;
            }

            MethodDecorator method = null;
            try {
                method = createDecoratedMethod(methodReference);

                if (method.isResolvableToType()) {
                    return;
                }
            } catch (MissingElementException e) {
            } catch (InvalidArgumentException e) {
            }

            if (method != null) {
                registerProblem(holder, method);
            }
        }

    }

    protected abstract MethodDecorator createDecoratedMethod(MethodReference reference) throws MissingElementException, InvalidArgumentException;

    protected abstract void registerProblem(ProblemsHolder holder, MethodDecorator element);

    protected boolean isEnabled(MethodReference methodReference) {
        RequireMethod methodAnnotation = getClass().getAnnotation(RequireMethod.class);
        if (methodAnnotation == null || !methodAnnotation.value().equalsIgnoreCase(methodReference.getName())) {
            return false;
        }

        DependsOnPlugin pluginAnnotation = getClass().getAnnotation(DependsOnPlugin.class);
        if (pluginAnnotation == null || !((StateComponentInterface) methodReference.getProject().getComponent(pluginAnnotation.value())).isEnabled()) {
            return false;
        }

        return true;
    }

}
