package pl.projectspace.idea.plugins.commons.php.code.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.psi.lookup.SimpleTextLookup;

import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class GenericMethodArgumentCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
        MethodReference method = PsiTreeUtil.getParentOfType(parameters.getPosition(), MethodReference.class);
        if (method == null) {
            return;
        }

        for (String item : getCompletions(method)) {
            completionResultSet.addElement(new SimpleTextLookup(item));
        }
    }

    protected abstract List<String> getCompletions(MethodReference method);

    protected abstract boolean isEnabled();
}
