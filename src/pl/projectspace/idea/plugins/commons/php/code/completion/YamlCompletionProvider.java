package pl.projectspace.idea.plugins.commons.php.code.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class YamlCompletionProvider extends CompletionProvider<CompletionParameters> {

    private ArrayList<LookupElement> lookupElements;

    YamlCompletionProvider(ArrayList<LookupElement> lookups) {
        lookupElements = lookups;
    }

    public void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet) {
        resultSet.addAllElements(lookupElements);
    }
}
