package pl.suzuyo.common.gui;

import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBList;

import javax.swing.*;
import java.util.*;

public class MyList<T> extends JBList<T> {

    private DefaultListModel<T> model;

    public MyList() {
        model = new DefaultListModel<>();
        setSelectionForeground(JBColor.LIGHT_GRAY);
        setSelectionBackground(JBColor.DARK_GRAY);
        setBorder(BorderFactory.createEmptyBorder());
        setCellRenderer(new DefaultListCellRenderer());
        setModel(model);
    }

    public void addItem(T item) {
        model.addElement(item);
    }

    public void addItemAndSetAsSelected(T item) {
        addItem(item);
        selectLastOne();
    }

    public void setItems(Collection<T> items) {
        removeAllItems();
        addItems(items);
    }

    public void addItems(Collection<T> items) {
        if (items != null) {
            items.forEach(this::addItem);
        }
    }

    public void downItem() {
        int index = getSelectedIndex();
        if (index >= 0 && index < model.getSize()) {
            int newIndex = index - 1;
            if (newIndex >= 0) {
                T value = model.remove(index);
                model.insertElementAt(value, newIndex);
                setSelectedIndex(newIndex);
            }
        }
    }

    public void upItem() {
        int index = getSelectedIndex();
        if (index >= 0 && index < model.getSize()) {
            int newIndex = index + 1;
            if (newIndex < model.getSize()) {
                T value = model.remove(index);
                model.insertElementAt(value, newIndex);
                setSelectedIndex(newIndex);
            }
        }
    }

    public void removeAllItems() {
        model.removeAllElements();
    }

    public T removeSelectedItem() {
        int selectedIndex = getSelectedIndex();
        T removedItem = model.getElementAt(selectedIndex);
        model.removeElementAt(selectedIndex);
        return removedItem;
    }

    public void selectLastOne() {
        setSelectedIndex(getItemsCount() - 1);
    }

    public void selectFirstOne() {
        setSelectedIndex(0);
    }

    public List<T> getItems() {
        List<T> items = new ArrayList<>();
        Enumeration<T> elements = model.elements();
        while (elements.hasMoreElements()) {
            items.add(elements.nextElement());
        }
        return items;
    }

    public T getItem(int index) {
        return model.getElementAt(index);
    }

    public void setAllSelectedValues() {
        int size = model.getSize();
        int[] indices = new int[size];
        for (int i = 0; i < size; i++) {
            indices[i] = i;
        }
        setSelectedIndices(indices);
    }

    public void removeAllSelectionValues() {
        removeSelectionInterval(0, model.getSize());
    }
}
