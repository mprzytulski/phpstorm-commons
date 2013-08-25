package pl.projectspace.idea.plugins.commons.php.utils;

import com.intellij.ide.IdeBundle;
import com.intellij.ide.actions.OpenFileAction;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.file.impl.FileManager;
import com.intellij.util.IncorrectOperationException;

import java.io.IOException;
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


    public VirtualFile createNamespaceDirectory(final VirtualFile dir, final String namespace) throws IOException {
        String[] parts = namespace.replaceAll("^/", "").replaceAll("/$", "").split("/");

        VirtualFile parentDir = dir;
        VirtualFile subDir = null;

        for (int i = 0; i < parts.length; i++) {
            subDir = parentDir.findChild(parts[i]);
            if (subDir == null) {
                parentDir = createSubdirectory(parentDir, parts[i]);
            } else {
                parentDir = subDir;
            }
        }

        return parentDir;
    }

    private VirtualFile createSubdirectory(final VirtualFile baseDir, final String name) throws IOException {
        final VirtualFile[] subDirectory = new VirtualFile[1];
        final IOException[] exception = new IOException[1];

        CommandProcessor.getInstance().executeCommand(project, new Runnable(){
            public void run() {
                subDirectory[0] = ApplicationManager.getApplication().runWriteAction(new Computable<VirtualFile>() {
                    public VirtualFile compute() {
                        try {
                            return baseDir.createChildDirectory(baseDir, name);
                        } catch (IOException e) {
                            exception[0] = e;
                        }
                        return null;
                    }
                });
            }
        }, IdeBundle.message("command.create.new.subdirectory"), null);

        if (exception[0] != null) throw exception[0];

        return subDirectory[0];
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

            PsiFile newFile = (PsiFile) directory.add(file);

            OpenFileAction.openFile(newFile.getVirtualFile(), project);
        }
    }

}
