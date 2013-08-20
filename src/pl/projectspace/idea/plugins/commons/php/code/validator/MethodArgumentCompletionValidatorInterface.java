package pl.projectspace.idea.plugins.commons.php.code.validator;

import com.intellij.psi.PsiElement;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public interface MethodArgumentCompletionValidatorInterface {
    public boolean isValidForCompletion(PsiElement methodReference);
}
