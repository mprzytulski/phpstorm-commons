package pl.projectspace.idea.plugins.commons.php.psi.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PhpMethodReference implements PsiReference {

    protected PsiElement method;
    protected PsiElement name;

    public PhpMethodReference(@NotNull Method method, @NotNull final PsiElement name) {
        this.method = method;
        this.name = name;
    }

    @Override
    public PsiElement getElement() {
        return name;
    }

    @Override
    public TextRange getRangeInElement() {
        return new TextRange(1, this.name.getTextLength() - 1);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return method;
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        return ((StringLiteralExpression)name).getContents();
    }

    @Override
    public PsiElement handleElementRename(String s) throws IncorrectOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isReferenceTo(PsiElement psiElement) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isSoft() {
        return true;
    }
}
