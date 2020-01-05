package pl.suzuyo.common.gui;

import com.intellij.openapi.ui.ValidationInfo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MyComponent {

    public final void init() {
        addFieldsListeners();
        initFieldsValues();
    }

    public abstract JPanel getRootPanel();

    public List<ValidationInfo> validate() {
        return new ArrayList<>();
    }

    public void initFieldsValues() {
    }

    protected void addFieldsListeners() {
    }
}
