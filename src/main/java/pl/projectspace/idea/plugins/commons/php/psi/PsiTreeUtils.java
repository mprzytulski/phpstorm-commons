package pl.projectspace.idea.plugins.commons.php.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PsiTreeUtils {

    private Project project;
    private PhpIndex index;

    public PsiTreeUtils(Project project, PhpIndex index) {
        this.project = project;
        this.index = index;
    }

    /**
     * Return first PhpClass instance from index based on FQN of this class
     *
     * @param fqn
     * @return
     */
    public PhpClass getClassByFQN(final String fqn, List<String> excludedNamespaces) {
        Collection<PhpClass> result = index.getClassesByFQN(fqn);
        if (!result.isEmpty()) {
            PhpClass phpClass = null;
            while ((phpClass = result.iterator().next()) != null) {
//                for (String ns : excludedNamespaces) {
//                    if (!phpClass.getNamespaceName().startsWith(ns)) {
                        return phpClass;
//                    }
//                }
            }
        }

        return null;
    }

    public PhpClass getClassByFQN(final String fqn) {
        return getClassByFQN(fqn, new ArrayList<String>());
    }

    /**
     * Return PhpClass instance based on given PhpMethodReference expression.
     *
     * This method will resolve class reference to find type of response and will return PhpClass based on class name
     * stored in type
     *
     * @param expression
     * @return
     */
    public PhpClass getClass(MethodReference expression, List<String> excludedNamespaces) {
//        || !expression.getClassReference().getType().equals(PhpType.OBJECT)
        // @todo - better check if given expression belongs to proper calls
        if (expression == null || expression.getClassReference() == null) {
            return null;
        }

        Pattern pattern = Pattern.compile("^([^\\\\]*)([\\\\a-zA-Z_0-9]*)(.*)$");
        Matcher matcher = pattern.matcher(expression.getClassReference().getType().toString());

        if (matcher.matches() && matcher.groupCount() == 3) {
            return getClassByFQN(matcher.group(2), excludedNamespaces);
        }

        return null;
    }

    /**
     * Return class for given PhpMethodReference expression (resolving reference)
     *
     * @param expression
     * @return
     */
    public PhpClass getClass(MethodReference expression) {
        return getClass(expression, new LinkedList<String>());
    }

    public PhpClass getClass(PsiElement element) throws MissingElementException {
        PhpClass phpClass = PsiTreeUtil.getParentOfType(element, PhpClass.class);
        if (phpClass == null) {
            throw new MissingElementException("Missing PhpClass as a parent for ");
        }

        return phpClass;
    }
}
