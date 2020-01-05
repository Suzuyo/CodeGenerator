package pl.suzuyo.generator;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import pl.suzuyo.NotFoundService;
import pl.suzuyo.PsiUtils;

import java.util.List;

class GeneratorActionEventFactory {

    GeneratorActionEvent createByActionEvent(AnActionEvent event) {
        Project project = event.getRequiredData(CommonDataKeys.PROJECT);
        PsiFile psiFile = event.getRequiredData(CommonDataKeys.PSI_FILE);
        Caret caret = event.getRequiredData(CommonDataKeys.CARET);
        PsiElement lastSelectedElement = PsiUtils.getSelectedElement(psiFile, caret);
        List<SelectionElement> selectedElements = PsiUtils.getAllSelectedElementsByType(psiFile, caret, lastSelectedElement.getClass());
        return new GeneratorActionEvent(project, psiFile, selectedElements);
    }

    static GeneratorActionEventFactory getInstance() {
        GeneratorActionEventFactory generatorActionEventFactory = ServiceManager.getService(GeneratorActionEventFactory.class);
        if (generatorActionEventFactory == null) {
            throw new NotFoundService("Cannot load generator action event factory");
        }
        return generatorActionEventFactory;
    }
}
