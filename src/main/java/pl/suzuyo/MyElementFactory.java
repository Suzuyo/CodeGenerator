package pl.suzuyo;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

import java.util.Arrays;
import java.util.List;

public class MyElementFactory {

    public List<PsiElement> createElementsInsideClassFromText(Project project, String text) {
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
        PsiClass classFromText = elementFactory.createClassFromText("class TempClass { " + text + " }", null);
        PsiElement[] children = classFromText.getInnerClasses()[0].getChildren();
        return Arrays.asList(children).subList(10, children.length - 2);
    }

    public static MyElementFactory getInstance() {
        MyElementFactory myElementFactory = ServiceManager.getService(MyElementFactory.class);
        if (myElementFactory == null) {
            throw new NotFoundService("Cannot load my element factory");
        }
        return myElementFactory;
    }
}