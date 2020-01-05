package pl.suzuyo.generator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

final class AnnotationWriter extends ElementWriter {
    private PsiElement psiElement;

    AnnotationWriter(String script, Project project, PsiElement psiElement) {
        super(script, project);
        this.psiElement = psiElement;
    }

    @Override
    void write() {
        PsiAnnotation annotation = JavaPsiFacade.getElementFactory(project).createAnnotationFromText(script, psiElement);
        if (psiElement instanceof PsiModifierListOwner) {
            PsiModifierListOwner psiModifierListOwner = (PsiModifierListOwner) psiElement;
            if (psiModifierListOwner.getModifierList() != null) {
                psiModifierListOwner.getModifierList().addAfter(annotation, null);
            }
        }
    }
}
