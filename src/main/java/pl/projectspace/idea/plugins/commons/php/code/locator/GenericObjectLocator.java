package pl.projectspace.idea.plugins.commons.php.code.locator;

import com.jetbrains.php.PhpIndex;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
abstract public class GenericObjectLocator implements ObjectLocatorInterface {

    protected PhpIndex index;

    public GenericObjectLocator(PhpIndex index) {
        this.index = index;
    }
}
