package pl.suzuyo.common.gui;

import com.intellij.openapi.ui.ComboBox;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyComboBox<T> extends ComboBox<T> {

    public void setItems(Collection<T> items) {
        removeAllItems();
        items.forEach(this::addItem);
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public T getSelectedItem() {
        return (T) super.getSelectedItem();
    }

    public List<T> getItems() {
        List<T> items = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            T item = getItemAt(i);
            items.add(item);
        }
        return items;
    }
}
