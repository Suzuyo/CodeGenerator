package pl.suzuyo.common.gui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public abstract class OperatedListPanel<T> extends JPanel {
    private OperatedListEvent<T> operatedListEvent;
    private T previousSelectedValue;
    private JButton removerButton;
    private JButton arrowDown;
    private JButton arrowUp;
    private MyList<T> list;

    public OperatedListPanel() {
        setLayout(new GridBagLayout());
        addOperationsPanel(this);
        addScrollList(this);
    }

    public void setOperatedListEvent(OperatedListEvent<T> operatedListEvent) {
        this.operatedListEvent = operatedListEvent;
    }

    public void setItems(Collection<T> items) {
        list.setItems(items);
    }

    public void selectFirstOne() {
        list.selectFirstOne();
    }

    public void selectLastIndex() {
        list.selectLastIndex();
    }

    public List<T> getItems() {
        return list.getItems();
    }

    public Set<T> getSetItems() {
        return new TreeSet<>(getItems());
    }

    public T getSelectedValue() {
        return list.getSelectedValue();
    }

    public T getPreviousSelectedValue() {
        return previousSelectedValue;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    protected abstract T createElement();

    public JButton getArrowDown() {
        return arrowDown;
    }

    public JButton getArrowUp() {
        return arrowUp;
    }

    private void addOperationsPanel(JPanel parentPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        addCreatorButton(panel);
        addRemoverButton(panel);
        addArrowUp(panel);
        addArrowDown(panel);
        panel.add(Box.createHorizontalGlue());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        parentPanel.add(panel, gridBagConstraints);
    }

    private void addCreatorButton(JPanel panel) {
        JButton creatorButton = new IconButton(AllIcons.General.Add);
        creatorButton.addActionListener(event -> addElement());
        panel.add(creatorButton);
    }

    private void addElement() {
        T element = createElement();
        if (element != null) {
            list.addItemAndSetAsSelected(element);
            if (operatedListEvent != null) {
                operatedListEvent.performAfterCreate(element);
            }
        }
    }

    private void addRemoverButton(JPanel panel) {
        removerButton = new IconButton(AllIcons.General.Remove);
        removerButton.addActionListener(event -> removeElement());
        panel.add(removerButton);
    }

    private void removeElement() {
        int response = Messages.showYesNoDialog("Are you sure you want to delete?", "Delete", Messages.getInformationIcon());
        if (response == 0) {
            T element = list.removeSelectedItem();
            if (element != null) {
                if (operatedListEvent != null) {
                    operatedListEvent.performAfterRemove(element);
                }
            }
        }
    }

    private void addArrowUp(JPanel panel) {
        arrowUp = new IconButton(AllIcons.General.ArrowUp);
        arrowUp.addActionListener(event -> operatedListEvent.performAfterArrow());
        arrowUp.addActionListener(event -> list.downItem());
        panel.add(arrowUp);
    }

    private void addArrowDown(JPanel panel) {
        arrowDown = new IconButton(AllIcons.General.ArrowDown);
        arrowDown.addActionListener(event -> operatedListEvent.performAfterArrow());
        arrowDown.addActionListener(event -> list.upItem());
        panel.add(arrowDown);
    }

    private void addScrollList(JPanel parentPanel) {
        list = new MyList<>();
        list.setListEvent(this::beforeSelectedElement);
        list.addListSelectionListener(event -> selectElement());
        JBScrollPane scrollPane = new JBScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(100, Integer.MAX_VALUE));
        scrollPane.setMaximumSize(new Dimension(100, Integer.MAX_VALUE));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        parentPanel.add(scrollPane, gridBagConstraints);
    }

    private void beforeSelectedElement() {
        operatedListEvent.performBeforeSelection2();
    }

    private void selectElement() {
        int selectedIndex = list.getSelectedIndex();
        arrowUp.setEnabled(selectedIndex > 0);
        arrowDown.setEnabled(selectedIndex < list.getItemsCount() - 1);
        T selectedValue = list.getSelectedValue();
        if (selectedValue != null) {
            if (previousSelectedValue != null && !previousSelectedValue.equals(selectedValue)) {
                if (operatedListEvent != null) {
                    operatedListEvent.performBeforeSelection(previousSelectedValue);
                }
            }
            if (operatedListEvent != null) {
                operatedListEvent.performAfterSelection(selectedValue);
            }
            removerButton.setEnabled(true);
        } else {
            operatedListEvent.performAfterRemoveSelection();
            removerButton.setEnabled(false);
        }
        previousSelectedValue = selectedValue;
    }
}
