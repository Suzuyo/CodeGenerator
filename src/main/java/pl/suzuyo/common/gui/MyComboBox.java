package pl.suzuyo.common.gui;

import com.intellij.openapi.ui.ComboBox;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

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
}
