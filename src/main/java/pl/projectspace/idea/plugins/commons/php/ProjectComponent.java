package pl.projectspace.idea.plugins.commons.php;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;
import org.jetbrains.annotations.NotNull;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ProjectComponent implements com.intellij.openapi.components.ProjectComponent {

    protected final Project project;
    protected final PhpIndex index;

    public ProjectComponent(Project project, PhpIndex index) {
        this.project = project;
        this.index = index;
    }

    @Override
    public void projectOpened() {
    }

    @Override
    public void projectClosed() {
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return null;
    }

    public PhpIndex getIndex() {
        return index;
    }

    public <T>T getService(@NotNull Class<T> service) {
        return (T) ServiceManager.getService(project, service);
    }

    public Project getProject() {
        return project;
    }
}
