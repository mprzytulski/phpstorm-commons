package pl.projectspace.idea.plugins.commons.php.code.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.code.resolver.NamedObjectResolverInterface;
import pl.projectspace.idea.plugins.commons.php.code.validator.MethodCallValidatorInterface;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class GenericMethodParameterInspection extends LocalInspectionTool {

    protected MethodCallValidatorInterface validator;
    protected NamedObjectResolverInterface resolver;
    protected LocalQuickFix quickFix;

    public GenericMethodParameterInspection(MethodCallValidatorInterface validator, NamedObjectResolverInterface resolver, LocalQuickFix quickFix) {
        this.validator = validator;
        this.resolver = resolver;
        this.quickFix = quickFix;
    }

    protected abstract void registerProblem(ProblemsHolder holder, PsiElement element);

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
            if (!validator.isValidDeclaration(methodReference)) {
                registerProblem(holder, methodReference);
            }
        }
    }

}
