package pl.projectspace.idea.plugins.commons.php.action;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class DirectoryAction extends AnAction {

    protected Project project;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        project = anActionEvent.getProject();

        DialogWrapper dialog = (DialogWrapper) getDialog();
        dialog.show();

        if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
            onOk(dialog);
        }
    }

    protected String getRelativeDirectory() {
        PsiDirectory selected = getSelectedDirectory();

        if (selected == null) {

        }

        return getSelectedDirectory().getVirtualFile().getPath().replace(project.getBasePath(), "");
    }

    protected PsiDirectory getSelectedDirectory() {
        ProjectView view = ProjectView.getInstance(project);
        PsiDirectory[] selected = view.getCurrentProjectViewPane().getSelectedDirectories();

        if (selected.length == 0) {
            Object[] elements = view.getCurrentProjectViewPane().getSelectedElements();
            for (Object element : elements) {
                if (element instanceof PsiFile) {
                    return ((PsiFile) element).getContainingDirectory();
                }
            }
            return null;
        }

        return selected[0];
    }

    protected abstract DialogWrapper getDialog();

    protected abstract String getActionDirectory();

    protected abstract void onOk(DialogWrapper dialog);

}
