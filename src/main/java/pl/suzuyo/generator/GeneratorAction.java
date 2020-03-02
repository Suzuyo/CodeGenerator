package pl.suzuyo.generator;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import pl.suzuyo.parameter.gui.ParametersDialog;
import pl.suzuyo.generator.gui.GeneratorDialog;
import pl.suzuyo.template.Template;

import java.util.*;

public class GeneratorAction extends AnAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        PsiFile psiFile = e.getRequiredData(CommonDataKeys.PSI_FILE);
        FileType fileType = psiFile.getFileType();
        boolean enableAction = fileType.equals(JavaFileType.INSTANCE);
        e.getPresentation().setEnabledAndVisible(enableAction);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        GeneratorActionEvent generatorActionEvent = GeneratorActionEventFactory.getInstance().createByActionEvent(event);
        Template template = new GeneratorDialog().showAndGetTemplate();
        if (template != null) {
            List<String> parametersNames = template.getParameters();
            Collections.sort(parametersNames);
            ParametersDialog parametersDialog = new ParametersDialog(parametersNames);
            Map<String, String> parameters = new HashMap<>();
            if (parametersNames.size() > 0) {
                parameters = parametersDialog.showAndGetParameters();
            }
            if (parametersNames.size() == 0 || parametersDialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
                List<ElementWriter> elementWriters = GeneratorActionService.getInstance().evaluate(generatorActionEvent, template, parameters);
                WriteCommandAction.runWriteCommandAction(generatorActionEvent.getProject(), () -> elementWriters.forEach(ElementWriter::write));
            }
        }
    }
}


/*else if (x.getValue() == SpecificTemplateType.CODE) {
    PsiCodeBlock codeBlockFromText = elementFactory.createCodeBlockFromText(x.getKey(), psiClass);
    PsiElement[] children = codeBlockFromText.getChildren();
    List<PsiElement> newChildren = Arrays.asList(children).subList(2, children.length - 1);
    Collections.reverse(newChildren);
    for (int  i = 0; i < newChildren.size(); i++) {
        if (i == newChildren.size() - 1) {
            firstSelectedElement.replace(newChildren.get(i));
        } else {
            firstSelectedElement.getParent().addAfter(newChildren.get(i), firstSelectedElement);
        }
    }
}*/