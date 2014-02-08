package pl.projectspace.idea.plugins.commons.php.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.StateComponentInterface;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.PluginAction;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class PhpClassAction extends AnAction {

    @Override
    public void update(AnActionEvent e) {
        final PhpClass phpClass = getPhpClassFromContext(e);

        boolean isAvailable = isAvailable(phpClass);

        e.getPresentation().setEnabled(
            isAvailable
        );

        e.getPresentation().setVisible(
                isAvailable
        );

        String label = getLabel(phpClass);

        if (label != null) {
            e.getPresentation().setText(label);
        }
    }

    public void actionPerformed(final AnActionEvent e) {
        final PhpClass phpClass = getPhpClassFromContext(e);
        perform(phpClass);
    }

    /**
     * Base check if current perform is available - depending on plugin state.
     *
     * @param phpClass
     * @return
     */
    protected boolean isAvailable(final PhpClass phpClass) {
        PluginAction annotation = this.getClass().getAnnotation(PluginAction.class);

        BaseComponent component = phpClass.getProject().getComponent(annotation.value());
        if (component instanceof StateComponentInterface) {
            return ((StateComponentInterface) component).isEnabled();
        }

        return false;
    }

    protected PhpClass getPhpClassFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        if (psiFile == null || editor == null) {
            return null;
        }

        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset);

        return PsiTreeUtil.getParentOfType(elementAt, PhpClass.class);
    }

    protected String getLabel(PhpClass phpClass) {
        return null;
    }

    /**
     * Perform perform
     *
     * @param phpClass
     */
    protected abstract void perform(final PhpClass phpClass);

}
