package pl.suzuyo.template.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.json.JsonFileType;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.fileChooser.FileSaverDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileWrapper;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import pl.suzuyo.PsiUtils;
import pl.suzuyo.common.gui.MyComponent;
import pl.suzuyo.common.gui.OperatedListEvent;
import pl.suzuyo.parameter.gui.ParametersComponent;
import pl.suzuyo.parameter.gui.ParametersEvent;
import pl.suzuyo.template.Template;

import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class TemplatesComponent extends MyComponent {
    private JPanel rootPanel;
    private JTextField templateNameField;
    private TemplateTypeComboBox templateTypeComboBox;
    private TemplateListPanel templateList;
    private JPanel initScriptPanel;
    private InitScriptComponent initScriptComponent;
    private JPanel parametersPanel;
    private JPanel scriptPanel;
    private JButton saveButton;
    private JButton importButton;
    private JButton exportButton;
    private JTabbedPane jTabbedPanel;
    private Document scriptDocument;
    private ParametersComponent parametersComponent;
    private boolean changeText;
    private Set<String> variablesNames;

    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }

    @Override
    protected void addFieldsListeners() {
        parametersComponent.setParametersEvent(new MyParametersEvent());
        templateList.setOperatedListEvent(new MyListEvent());
        saveButton.addActionListener(event -> updateTemplateByFields());
        importButton.addActionListener(event -> importTemplates());
        exportButton.addActionListener(event -> exportTemplates());
        scriptDocument.addDocumentListener(new MyDocumentListener());
        initScriptComponent.addDocumentListener(new MyDocumentListener());
        templateNameField.getDocument().addDocumentListener(new MyDocumentListener());
    }

    @Override
    public void initFieldsValues() {
        templateTypeComboBox.loadValues();
        templateList.loadFromStorage();
        if (templateList.isEmpty()) {
            setFieldsAsEditable(false);
        }
        parametersComponent.init();
    }

    private void setFieldsAsEditable(boolean editable) {
        templateNameField.setEnabled(editable);
        scriptDocument.setReadOnly(!editable);
        initScriptComponent.setFieldsAsEditable(editable);
        parametersComponent.setFieldsAsEditable(editable);
        saveButton.setEnabled(false);
    }

    private void setFieldsByTemplate(Template template) {
        templateNameField.setText(template.getName());
        templateTypeComboBox.setSelectedItem(template.getType());
        parametersComponent.setAddedParameters(template.getParameters());
        parametersComponent.setParameters(new ArrayList<>(template.getVariablesWithoutParameters()));
        parametersComponent.initFieldsValues();
        initScriptComponent.setScript(template.getInitScript());
        variablesNames = template.getVariables();
        initScriptComponent.setCompleteVariables(variablesNames);
        PsiUtils.setTextForDocument(scriptDocument, template.getScript());
    }

    private void clearFields() {
        templateNameField.setText("");
        initScriptComponent.clearFieldsValues();
        parametersComponent.clearFieldsValues();
        templateTypeComboBox.setSelectedItem(null);
        PsiUtils.setTextForDocument(scriptDocument, "");
    }

    private void updateTemplateByFields() {
        Template template = templateList.getSelectedValue();
        if (template != null) {
            template.setType(templateTypeComboBox.getSelectedItem());
            if (!templateNameField.getText().isEmpty()) {
                template.setName(templateNameField.getText());
            }
            template.setInitScript(initScriptComponent.getScript());
            template.setScript(scriptDocument.getText());
            variablesNames = template.getVariables();
            int countParameters = template.getParameters().size();
            List<String> templateParameters = new ArrayList<>(template.getParameters());
            Collections.sort(templateParameters);
            for (int i = 0; i < templateParameters.size(); i++) {
                String parameter = templateParameters.get(i);
                template.removeParameter(parameter);
                if (template.getParameters().size() == 0) {
                    for (int j = 0; j < templateParameters.size(); j++) {
                        parameter = templateParameters.get(j);
                        template.addParameter(parameter);
                    }
                }
            }
            if (variablesNames.size() < countParameters || (countParameters > 0 && parametersComponent.getParametersCount() != variablesNames.size())) {
                for (int i = 0; i < countParameters; i++) {
                    Iterator<String> variablesList = variablesNames.iterator();
                    boolean foundParameter = false;
                    String first = templateParameters.get(i);
                    while (variablesList.hasNext()) {
                        String second = variablesList.next();
                        if (first.equals(second)) {
                            foundParameter = true;
                            break;
                        }
                    }
                    if (!foundParameter) {
                        template.removeParameter(first);
                    }
                }
            }
            templateList.saveToStorage();
            templateList.loadFromStorage();
            saveButton.setEnabled(false);
            changeText = false;
        }
    }

    private void createUIComponents() {
        initScriptComponent = new InitScriptComponent();
        initScriptPanel = initScriptComponent.getRootPanel();
        parametersComponent = new ParametersComponent();
        parametersPanel = parametersComponent.getRootPanel();
        scriptDocument = EditorFactory.getInstance().createDocument("");
        Editor editor = EditorFactory.getInstance().createEditor(scriptDocument);
        scriptPanel = (JPanel) editor.getComponent();
    }

    private void importTemplates() {
        try {
            FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(true, false, false, false, false, false);
            fileChooserDescriptor.withFileFilter(virtualFile -> virtualFile.getFileType().equals(JsonFileType.INSTANCE));
            Project defaultProject = ProjectManager.getInstance().getDefaultProject();
            VirtualFile[] choose = FileChooserFactory.getInstance().createFileChooser(fileChooserDescriptor, defaultProject, null)
                    .choose(defaultProject);
            if (choose.length == 1) {
                Template[] importTemplates = new ObjectMapper().readValue(choose[0].getInputStream(), Template[].class);
                TemplatesFilterDialog templatesFilterDialog = new TemplatesFilterDialog();
                templatesFilterDialog.setDescription("Select templates to import");
                templatesFilterDialog.setTemplates(Arrays.asList(importTemplates));
                List<Template> filterImportTemplates = templatesFilterDialog.showAndGetTemplates();
                List<Template> templates = templateList.getItems();
                if (templatesFilterDialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
                    if (templates.size() > 0) {
                        for (int i = 0; i < filterImportTemplates.size(); i++) {
                            String first = filterImportTemplates.get(i).getName();
                            for (int j = 0; j < templates.size(); j++) {
                                String second = templates.get(j).getName();
                                if (first.equals(second)) {
                                    TemplatesImportDialog templatesImportDialog = new TemplatesImportDialog(first);
                                    templatesImportDialog.showAndGet();
                                    if (templatesImportDialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
                                        templates.set(j, filterImportTemplates.get(i));
                                        templateList.setItems(templates);
                                        templateList.saveToStorage();
                                    }
                                    break;
                                }
                                if ((j + 1) == templates.size()) {
                                    templates.add(filterImportTemplates.get(i));
                                    templateList.setItems(templates);
                                    templateList.saveToStorage();
                                    break;
                                }
                            }
                        }
                    } else {
                        for (Template filterImportTemplate : filterImportTemplates) {
                            templates.add(filterImportTemplate);
                            templateList.setItems(templates);
                            templateList.saveToStorage();
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Import failed", e);
        }
    }

    private void exportTemplates() {
        try {
            FileSaverDescriptor fileSaverDescriptor = new FileSaverDescriptor("", "", "json");
            Project defaultProject = ProjectManager.getInstance().getDefaultProject();
            VirtualFileWrapper virtualFileWrapper = FileChooserFactory.getInstance().createSaveFileDialog(fileSaverDescriptor, defaultProject)
                    .save(null, "templates.json");
            if (virtualFileWrapper != null) {
                File file = virtualFileWrapper.getFile();
                VirtualFile virtualFile = virtualFileWrapper.getVirtualFile();
                TemplatesFilterDialog templatesFilterDialog = new TemplatesFilterDialog();
                templatesFilterDialog.setDescription("Select templates to export");
                templatesFilterDialog.setTemplates(templateList.getItems());
                List<Template> filterTemplates = templatesFilterDialog.showAndGetTemplates();
                if (templatesFilterDialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
                    new ObjectMapper().writeValue(file, filterTemplates);
                    if (virtualFile != null) {
                        virtualFile.refresh(false, false);
                    }
                }
            }
        } catch (IOException e) {
            String message = e.getMessage();
            Messages.showErrorDialog(message, "Export Error");
            throw new RuntimeException("Export failed", e);
        }
    }

    private class MyParametersEvent implements ParametersEvent {

        @Override
        public void performAfterAdd(String parameter) {
            templateList.getSelectedValue().addParameter(parameter);
        }

        @Override
        public void performAfterRemove(String parameter) {
            templateList.getSelectedValue().removeParameter(parameter);
        }
    }

    private class MyListEvent implements OperatedListEvent<Template> {

        @Override
        public void performAfterCreate(Template template) {
            setFieldsByTemplate(template);
            setFieldsAsEditable(true);
            templateList.saveToStorage();
        }

        @Override
        public void performAfterRemove(Template template) {
            clearFields();
            setFieldsAsEditable(false);
            templateList.saveToStorage();
        }

        @Override
        public void performAfterRemoveSelection() {

        }

        @Override
        public void performAfterArrow() {
             templateList.saveToStorage();
        }

        @Override
        public void performBeforeSelection2() {
            if (changeText) {
                int response = Messages.showYesNoDialog("Do you want to save the changes?", "Save", Messages.getInformationIcon());
                if (response == 0) {
                    Template template = templateList.getSelectedValue();
                    template.setName(templateNameField.getText());
                    template.setInitScript(initScriptComponent.getScript());
                    template.setScript(scriptDocument.getText());
                    templateList.saveToStorage();
                }
            }
        }

        @Override
        public void performBeforeSelection(Template template) {

        }

        @Override
        public void performAfterSelection(Template template) {
            setFieldsByTemplate(template);
            setFieldsAsEditable(true);
        }
    }

    private class MyDocumentListener implements DocumentListener, javax.swing.event.DocumentListener {

        @Override
        public void documentChanged(@NotNull DocumentEvent event) {
            change();
        }

        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            change();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            change();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            change();
        }
    }

    private void change() {
        Template template = templateList.getSelectedValue();
        if (template != null) {
            String templateScript = template.getScript();
            String changedScript = scriptDocument.getText();
            String templateInitScript = template.getInitScript();
            String changedInitScript = initScriptComponent.getScript();
            String templateName = template.getName();
            String changedTemplateName = templateNameField.getText();
            if (!templateScript.equals(changedScript) || !templateInitScript.equals(changedInitScript) || !templateName.equals(changedTemplateName)) {
                saveButton.setEnabled(true);
                saveButton.setForeground(JBColor.RED);
                changeText = true;
            } else {
                saveButton.setEnabled(false);
                saveButton.setBackground(JBColor.background());
                changeText = false;
            }
        }
    }
}
