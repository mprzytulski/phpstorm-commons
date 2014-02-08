package pl.projectspace.idea.plugins.commons.php.code.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.components.BaseComponent;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.StateComponentInterface;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.DependsOnPlugin;

public abstract class PhpClassInspection extends LocalInspectionTool {

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        DependsOnPlugin annotation = this.getClass().getAnnotation(DependsOnPlugin.class);

        BaseComponent component = holder.getProject().getComponent(annotation.value());
        if (component instanceof StateComponentInterface) {
            if (((StateComponentInterface) component).isEnabled()) {
                return getVisitor(holder);
            }
        }

        return new DummyPhpElementVisitor();
    }

    protected abstract PhpElementVisitor getVisitor(@NotNull ProblemsHolder holder);
}
