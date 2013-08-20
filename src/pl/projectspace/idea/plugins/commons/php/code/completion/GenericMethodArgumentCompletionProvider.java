package pl.projectspace.idea.plugins.commons.php.code.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.code.locator.ObjectLocatorInterface;
import pl.projectspace.idea.plugins.commons.php.code.validator.MethodArgumentCompletionValidatorInterface;
import pl.projectspace.idea.plugins.commons.php.psi.lookup.SimpleTextLookup;

import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class GenericMethodArgumentCompletionProvider extends CompletionProvider<CompletionParameters> {

    protected ObjectLocatorInterface locator;
    protected MethodArgumentCompletionValidatorInterface validator;

    public GenericMethodArgumentCompletionProvider(ObjectLocatorInterface locator, MethodArgumentCompletionValidatorInterface validator) {
        super();
        this.locator = locator;
        this.validator = validator;
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
        if (!validator.isValidForCompletion(parameters.getPosition())) {
            return;
        }

        ensureLocator(parameters.getPosition());

        for (String item : getCompletions()) {
            completionResultSet.addElement(new SimpleTextLookup(item));
        }
    }

    protected void ensureLocator(PsiElement element) {
    }

    protected abstract List<String> getCompletions();
}
