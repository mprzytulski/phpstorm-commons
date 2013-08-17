package pl.projectspace.idea.plugins.commons.php.service.locator;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
abstract public class BasePhpClassLocator implements PhpClassLocatorInterface {

    protected Project project;
    protected PhpIndex index;

    public BasePhpClassLocator(Project project, PhpIndex index) {
        this.project = project;
        this.index = index;
    }

}
