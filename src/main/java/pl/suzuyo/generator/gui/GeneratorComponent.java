package pl.suzuyo.generator.gui;

import pl.suzuyo.common.gui.MyComponent;
import pl.suzuyo.template.gui.TemplateComboBox;
import pl.suzuyo.template.Template;
import pl.suzuyo.template.gui.TemplatesDialog;

import javax.swing.*;

class GeneratorComponent extends MyComponent {
    private JPanel rootPanel;
    private JButton templatesDialogOpener;
    private TemplateComboBox templateComboBox;

    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }

    @Override
    protected void addFieldsListeners() {
        templatesDialogOpener.addActionListener(event -> openTemplatesDialog());
    }

    @Override
    public void initFieldsValues() {
        templateComboBox.loadFromStorage();
    }

    Template getSelectedTemplate() {
        return templateComboBox.getSelectedItem();
    }

    void activeTemplateComboBox() {
        templateComboBox.requestFocus();
    }

    private void openTemplatesDialog() {
        new TemplatesDialog().showAndGet();
        initFieldsValues();
    }
}
