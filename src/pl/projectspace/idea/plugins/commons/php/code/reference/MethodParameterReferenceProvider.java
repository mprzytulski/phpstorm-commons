package pl.projectspace.idea.plugins.commons.php.code.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.code.validator.MethodCallValidatorInterface;
import pl.projectspace.idea.plugins.commons.php.code.resolver.NamedObjectResolverInterface;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
abstract public class MethodParameterReferenceProvider extends PsiReferenceProvider {

    protected MethodCallValidatorInterface validator;

    public MethodParameterReferenceProvider(MethodCallValidatorInterface validator, NamedObjectResolverInterface resolver) {
        this.validator = validator;
    }

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext processingContext) {

        if (!(element instanceof MethodReference)) {

        }

        MethodReference methodReference = (MethodReference) element;

//        if (validator.isValidCall(methodReference)) {
//            return new PsiReference[0];
//        }

        return new PsiReference[0];
    }

    protected PhpClass getTargetClass(MethodReference methodReference) throws Exception {
        PhpClass phpClass = PsiTreeUtil.getParentOfType(methodReference, PhpClass.class);
        if (phpClass == null) {
            throw new Exception("test");
        }

        return phpClass;
    }

}
