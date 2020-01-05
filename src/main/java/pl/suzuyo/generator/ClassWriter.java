package pl.suzuyo.generator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import pl.suzuyo.MyElementFactory;

import java.util.List;

final class ClassWriter extends ElementWriter {
    private PsiElement psiElement;

    ClassWriter(String script, Project project, PsiClass psiClass) {
        super(script, project);
        this.psiElement = psiClass;
    }

    ClassWriter(String script, Project project, PsiFile psiFile) {
        super(script, project);
        this.psiElement = psiFile;
    }

    @Override
    public void write() {
        List<PsiElement> elements = MyElementFactory.getInstance().createElementsInsideClassFromText(project, script);
        elements.forEach(psiElement::add);
    }
}
