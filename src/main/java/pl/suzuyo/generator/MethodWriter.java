package pl.suzuyo.generator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import pl.suzuyo.MyElementFactory;

import java.util.List;

final class MethodWriter extends ElementWriter {
    private PsiClass psiClass;

    MethodWriter(String script, Project project, PsiClass psiClass) {
        super(script, project);
        this.psiClass = psiClass;
    }

    @Override
    public void write() {
        List<PsiElement> elements = MyElementFactory.getInstance().createElementsInsideClassFromText(project, script);
        elements.forEach(psiClass::add);
    }
}
