package pl.suzuyo.template.gui;

import com.intellij.openapi.ui.ValidationInfo;
import pl.suzuyo.common.gui.MyComponent;
import pl.suzuyo.template.Template;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class NewTemplateComponent extends MyComponent {
    private JPanel rootPanel;
    private JTextField templateNameField;
    private TemplateTypeComboBox templateTypeField;

    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }

    @Override
    public List<ValidationInfo> validate() {
        List<ValidationInfo> validationInfoList = new LinkedList<>();
        if (templateNameField.getText().isEmpty()) {
            validationInfoList.add(new ValidationInfo("Name cannot be empty", templateNameField));
        }
        if (templateTypeField.getSelectedItem() == null) {
            validationInfoList.add(new ValidationInfo("Type cannot be empty", templateTypeField));
        }
        return validationInfoList;
    }

    @Override
    public void initFieldsValues() {
        templateTypeField.loadValues();
    }

    public Template createTemplate() {
        Template template = new Template();
        template.setName(templateNameField.getText());
        template.setType(templateTypeField.getSelectedItem());
        return template;
    }
}
