package pl.projectspace.idea.plugins.commons.php.utils;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;

import java.util.Properties;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class FileFactory {

    private Project project;

    public FileFactory(Project project) {
        this.project = project;
    }

    public CreateFileFromTemplateWriteAction getCreateFileFromTemplateWriteAction(VirtualFile dir, String fileName, FileType fileType, String templateName, Properties properties) {
        return new CreateFileFromTemplateWriteAction(project, dir, fileName, fileType, templateName, properties);
    }

    private static class CreateFileFromTemplateWriteAction implements Runnable {

        private Project project;
        private VirtualFile dir;
        private String fileName;
        private FileType fileType;
        private String templateName;
        private Properties properties;

        public CreateFileFromTemplateWriteAction(Project project, VirtualFile dir, String fileName, FileType fileType, String templateName, Properties properties) {
            this.project = project;
            this.dir = dir;
            this.fileName = fileName;
            this.fileType = fileType;
            this.templateName = templateName;
            this.properties = properties;
        }

        @Override
        public void run() {
            final FileTemplate template = FileTemplateManager.getInstance().getJ2eeTemplate(templateName);

            String text;
            try {
                text = template.getText(properties);
            }
            catch (Exception e) {
                throw new RuntimeException("Unable to load template for " + FileTemplateManager.getInstance().internalTemplateToSubject(templateName), e);
            }

            final PsiFileFactory factory = PsiFileFactory.getInstance(project);
            final PsiFile file = factory.createFileFromText(fileName, fileType, text);

            PsiDirectory directory = file.getManager().findDirectory(dir);

            directory.add(file);
        }
    }

}
