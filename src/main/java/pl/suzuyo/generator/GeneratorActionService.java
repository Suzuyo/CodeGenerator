package pl.suzuyo.generator;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiFieldImpl;
import com.intellij.psi.impl.source.PsiParameterImpl;
import com.intellij.psi.impl.source.PsiTypeElementImpl;
import com.intellij.psi.impl.source.tree.java.PsiLiteralExpressionImpl;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import pl.suzuyo.NotFoundService;
import pl.suzuyo.template.ScriptService;
import pl.suzuyo.template.Template;
import pl.suzuyo.template.TemplateType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GeneratorActionService {

    List<ElementWriter> evaluate(GeneratorActionEvent generatorActionEvent, Template template, Map<String, String> parameters) {
        String initScript = template.getInitScript();
        Project project = generatorActionEvent.getProject();
        List<ElementWriter> elementWriters = new ArrayList<>();
        ScriptService scriptService = ScriptService.getInstance();
        for (SelectionElement selectionElement : generatorActionEvent.getSelectedElements()) {
            Map<String, String> variables = new HashMap<>();
            if (initScript != null && !initScript.isEmpty()) {
                Binding binding = createBinding(selectionElement, variables, parameters);
                new GroovyShell(binding).evaluate(initScript);
            }
            String script = template.getScript();
            String newScript = variables.isEmpty() ? script : scriptService.setVariables(script, variables);
            if (template.getType() == TemplateType.CLASS) {
                PsiClass psiClass = selectionElement.getPsiClass();
                PsiFile psiFile = selectionElement.getPsiFile();
                ClassWriter classWriter = (psiClass != null)
                        ? new ClassWriter(newScript, project, psiClass)
                        : new ClassWriter(newScript, project, psiFile);
                elementWriters.add(classWriter);
            } else if (template.getType() == TemplateType.METHOD) {
                MethodWriter methodWriter = new MethodWriter(newScript, project, selectionElement.getPsiClass());
                elementWriters.add(methodWriter);
            } else if (template.getType() == TemplateType.ANNOTATION) {
                AnnotationWriter annotationWriter = new AnnotationWriter(newScript, project, selectionElement.getPsiElement());
                elementWriters.add(annotationWriter);
            }
        }
        if (elementWriters.isEmpty()) {
            elementWriters.add(new ClassWriter(template.getScript(), project, generatorActionEvent.getFile()));
        }
        return elementWriters;
    }

    private Binding createBinding(SelectionElement selectionElement, Map<String, String> variables, Map<String, String> parameters) {
        Binding binding = new Binding();
        binding.setVariable("activeFile", selectionElement.getPsiFile());
        binding.setVariable("activeClass", selectionElement.getPsiClass());
        binding.setVariable("parameters", parameters);
        binding.setVariable("variables", variables);
        binding.setVariable("selectedField", null);
        binding.setVariable("selectedType", null);
        binding.setVariable("selectedParameter", null);
        binding.setVariable("selectedLiteral", null);
        PsiElement psiElement = selectionElement.getPsiElement();
        if (psiElement instanceof PsiFieldImpl) {
            binding.setVariable("selectedField", psiElement);
        } else if (psiElement instanceof PsiTypeElementImpl) {
            binding.setVariable("selectedType", psiElement);
        } else if (psiElement instanceof PsiParameterImpl) {
            binding.setVariable("selectedParameter", psiElement);
        } else if (psiElement instanceof PsiLiteralExpressionImpl) {
            binding.setVariable("selectedLiteral", psiElement);
        }
        return binding;
    }

    static GeneratorActionService getInstance() {
        GeneratorActionService service = ServiceManager.getService(GeneratorActionService.class);
        if (service == null) {
            throw new NotFoundService("Cannot load generator action service");
        }
        return service;
    }
}
