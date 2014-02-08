package pl.projectspace.idea.plugins.commons.php.psi.element;

import com.intellij.ide.IconProvider;
import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.commons.php.StateComponentInterface;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.DependsOnPlugin;

import javax.swing.*;

public abstract class PluginIconProvider extends IconProvider {
    @Nullable
    @Override
    public Icon getIcon(@NotNull PsiElement psiElement, @Iconable.IconFlags int i) {
        if (!isEnabled(psiElement)) {
            return null;
        }

        return getIconForElement(psiElement, i);
    }

    protected abstract Icon getIconForElement(@NotNull PsiElement element, @Iconable.IconFlags int i);

    private boolean isEnabled(PsiElement psiElement) {
        DependsOnPlugin annotation = this.getClass().getAnnotation(DependsOnPlugin.class);

        BaseComponent component = psiElement.getProject().getComponent(annotation.value());
        if (component instanceof StateComponentInterface) {
            return ((StateComponentInterface) component).isEnabled();
        }

        return false;
    }
}
