package pl.suzuyo.parameter.gui;

import pl.suzuyo.common.gui.MyComboBox;
import pl.suzuyo.common.gui.MyComponent;
import pl.suzuyo.common.gui.MyList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.List;

public class ParametersComponent extends MyComponent {
    private ParametersEvent parametersEvent;
    private JPanel rootPanel;
    private JButton parameterAddButton;
    private JButton parameterRemoveButton;
    private MyComboBox<String> parameterComboBox;
    private MyList<String> addedParameterList;

    public void setParametersEvent(ParametersEvent parametersEvent) {
        this.parametersEvent = parametersEvent;
    }

    public void setAddedParameters(List<String> addedParameters) {
        addedParameterList.setItems(addedParameters);
    }

    public void setParameters(List<String> parameters) {
        parameterComboBox.setItems(parameters);
    }

    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }

    @Override
    protected void addFieldsListeners() {
        parameterAddButton.addActionListener(event -> addParameter());
        parameterRemoveButton.addActionListener(event -> removeParameter());
        addedParameterList.addListSelectionListener(this::selectAddedParameters);
    }

    public void clearFieldsValues() {
        addedParameterList.removeAllItems();
        parameterComboBox.setSelectedItem(null);
    }

    public void setFieldsAsEditable(boolean editable) {
        parameterAddButton.setEnabled(editable);
        parameterComboBox.setEnabled(editable);
    }

    private void addParameter() {
        String selectedItem = parameterComboBox.getSelectedItem();
        if (selectedItem != null) {
            if (parametersEvent != null) {
                parametersEvent.performAfterAdd(selectedItem);
            }
            addedParameterList.addItem(selectedItem);
            parameterComboBox.removeItemAt(parameterComboBox.getSelectedIndex());
            if (parameterComboBox.getItemCount() == 0) {
                parameterAddButton.setEnabled(false);
            }
        }
    }

    private void removeParameter() {
        String selectedItem = addedParameterList.removeSelectedItem();
        if (parametersEvent != null) {
            parametersEvent.performAfterRemove(selectedItem);
        }
        parameterComboBox.addItem(selectedItem);
        parameterRemoveButton.setEnabled(false);
        parameterAddButton.setEnabled(true);
    }

    private void selectAddedParameters(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            parameterRemoveButton.setEnabled(true);
        }
    }
}
