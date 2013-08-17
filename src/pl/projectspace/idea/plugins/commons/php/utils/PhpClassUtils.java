package pl.projectspace.idea.plugins.commons.php.utils;

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

}
