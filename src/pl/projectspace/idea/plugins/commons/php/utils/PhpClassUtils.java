package pl.projectspace.idea.plugins.commons.php.utils;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.jetbrains.php.lang.psi.elements.PhpClass;

import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PhpClassUtils {

    /**
     * Check if given Page belongs to project test scope
     *
     * @param phpClass
     * @return
     */
    public static boolean isInExcludedNamespace(PhpClass phpClass, List<String> excludedNamespaces) {
        return excludedNamespaces.contains(phpClass.getNamespaceName());
    }

    public static String getPSRPathFromClassNamespace(String namespace) {
        return namespace.replaceAll("\\\\", "/");
    }

    public static LeafPsiElement getClassNameIdentifierFrom(PhpClass phpClass) {
        PsiElement child = phpClass.getFirstChild();
        do {
            if (child instanceof LeafPsiElement && ((LeafPsiElement) child).getElementType().toString().equals("identifier")) {
                return (LeafPsiElement) child;
            }
        } while((child = child.getNextSibling()) != null);

        return null;
    }

}
