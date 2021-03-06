package pl.suzuyo.template.gui;

import pl.suzuyo.common.gui.MyComponent;
import pl.suzuyo.common.gui.MyList;
import pl.suzuyo.template.Template;

import javax.swing.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TemplatesFilterComponent extends MyComponent {
    private JPanel rootPanel;
    private MyList<Template> templatesList;
    private JButton selectAllButton;
    private JButton clearButton;
    private JLabel descriptionLabel;

    public void setTemplates(List<Template> templates) {
        templatesList.setItems(templates);
    }

    public List<Template> getTemplates() {
        return templatesList.getSelectedValuesList();
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }

    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }

    @Override
    protected void addFieldsListeners() {
        selectAllButton.addActionListener(event -> templatesList.setAllSelectedValues());
        clearButton.addActionListener(event -> templatesList.removeAllSelectionValues());
    }
}
