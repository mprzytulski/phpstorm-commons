package pl.projectspace.idea.plugins.commons.php.code.validator;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GenericMethodCallValidator implements MethodCallValidatorInterface, MethodArgumentCompletionValidatorInterface {

    protected final String methodName;
    protected final Class[] parameterTypes;

    public GenericMethodCallValidator(String methodName, Class[] parameterTypes) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
    }

    @Override
    public boolean isValidDeclaration(MethodReference methodReference) {
        return (
            isValidMethodName(methodReference)
            && hasValidParameters(methodReference)
        );
    }

    protected boolean isValidMethodName(MethodReference methodReference) {
        return methodReference.getName().equalsIgnoreCase(methodName);
    }

    protected boolean hasValidParameters(MethodReference methodReference) {
        PsiElement[] params = methodReference.getParameters();

        if (params.length != parameterTypes.length) {
            return false;
        }

        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i] != null && !params[i].getClass().isInstance(parameterTypes[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isValidForCompletion(PsiElement element) {
        MethodReference methodReference = PsiTreeUtil.getParentOfType(element, MethodReference.class);

        return (methodReference != null && isValidMethodName(methodReference) && (element instanceof LeafPsiElement));
    }
}
