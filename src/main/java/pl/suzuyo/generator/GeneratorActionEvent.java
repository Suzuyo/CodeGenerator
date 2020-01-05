package pl.suzuyo.generator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

import java.util.List;

class GeneratorActionEvent {
    private Project project;
    private PsiFile file;
    private List<SelectionElement> selectedElements;

    GeneratorActionEvent(Project project, PsiFile file, List<SelectionElement> selectedElements) {
        this.project = project;
        this.file = file;
        this.selectedElements = selectedElements;
    }

    Project getProject() {
        return project;
    }

    PsiFile getFile() {
        return file;
    }

    List<SelectionElement> getSelectedElements() {
        return selectedElements;
    }
}
