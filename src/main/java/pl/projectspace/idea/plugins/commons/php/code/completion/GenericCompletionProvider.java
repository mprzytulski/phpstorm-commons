package pl.projectspace.idea.plugins.commons.php.code.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.components.BaseComponent;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.StateComponentInterface;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.DependsOnPlugin;


public abstract class GenericCompletionProvider extends CompletionProvider<CompletionParameters> {

    public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet) {
        if (!isEnabled(parameters.getPosition())) {
            return;
        }

        addCompletionsFor(parameters, context, resultSet);
    }

    protected boolean isEnabled(PsiElement element) {
        DependsOnPlugin annotation = this.getClass().getAnnotation(DependsOnPlugin.class);

        BaseComponent component = element.getProject().getComponent(annotation.value());
        if (component instanceof StateComponentInterface) {
            return ((StateComponentInterface) component).isEnabled();
        }

        return false;
    }

    protected abstract void addCompletionsFor(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet);

}
