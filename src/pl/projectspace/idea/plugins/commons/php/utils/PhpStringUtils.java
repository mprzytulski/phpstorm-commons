package pl.projectspace.idea.plugins.commons.php.utils;

import org.apache.commons.lang.WordUtils;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PhpStringUtils {

    public static String normaliseToClassName(String str) {
        return WordUtils.capitalize(str).trim().replaceAll(" ", "");
    }

    public static boolean belongsToNamespace(String fqn, String namespace) {
        return fqn.replaceAll("^\\\\", "").startsWith(namespace.replaceAll("^\\\\", ""));
    }

}
