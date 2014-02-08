package pl.projectspace.idea.plugins.commons.php.code.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.components.BaseComponent;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.StateComponentInterface;
import pl.projectspace.idea.plugins.commons.php.psi.lookup.SimpleTextLookup;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.DependsOnPlugin;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.RequireMethod;

import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class GenericMethodArgumentCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
        MethodReference method = PsiTreeUtil.getParentOfType(parameters.getPosition(), MethodReference.class);
        if (method == null || !isEnabled(method)) {
            return;
        }

        RequireMethod methodAnnotation = this.getClass().getAnnotation(RequireMethod.class);
        if (methodAnnotation != null && !methodAnnotation.value().equalsIgnoreCase(method.getName())) {
            return;
        }

        for (String item : getCompletions(method)) {
            completionResultSet.addElement(new SimpleTextLookup(item));
        }
    }

    protected boolean isEnabled(PsiElement element) {
        DependsOnPlugin annotation = this.getClass().getAnnotation(DependsOnPlugin.class);
        BaseComponent component = element.getProject().getComponent(annotation.value());

        return ((StateComponentInterface) component).isEnabled();
    }

    protected abstract List<String> getCompletions(MethodReference method);
}
