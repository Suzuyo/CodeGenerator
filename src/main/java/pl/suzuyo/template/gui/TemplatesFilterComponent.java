package pl.suzuyo.template.gui;

import pl.suzuyo.common.gui.MyComponent;
import pl.suzuyo.template.Template;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TemplatesFilterComponent extends MyComponent {
    private JPanel rootPanel;
    private List<Template> templates;
    private JButton selectAllButton;
    private JButton clearButton;
    private JLabel descriptionLabel;
    private JPanel panel1;
    private JCheckBox[] checkBox;

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
        int size = templates.size();
        checkBox = new JCheckBox[size];
        for (int i = 0; i < size; i++) {
            checkBox[i] = new JCheckBox(templates.get(i).getName());
            panel1.add(checkBox[i]);
            panel1.add(Box.createRigidArea(new Dimension(0, 5)));
        }
    }

    public List<Template> getTemplates() {
        int size = templates.size();
        List<Template> selectedTemplates = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (checkBox[i].isSelected()) {
                selectedTemplates.add(templates.get(i));
            }
        }
        return selectedTemplates;
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
        selectAllButton.addActionListener(event -> setAllSelectedValues());
        clearButton.addActionListener(event -> removeAllSelectionValues());
    }

    private void setAllSelectedValues() {
        setAllSelectedValues(true);
    }

    private void removeAllSelectionValues() {
        setAllSelectedValues(false);
    }

    private void setAllSelectedValues(boolean selected) {
        int size = templates.size();
        for (int i = 0; i < size; i++) {
            checkBox[i].setSelected(selected);
        }
    }

    private void createUIComponents() {
        panel1 = new JPanel();
        BoxLayout boxLayout = new BoxLayout(panel1, BoxLayout.Y_AXIS);
        panel1.setLayout(boxLayout);
    }
}
