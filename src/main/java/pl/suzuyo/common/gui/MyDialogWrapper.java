package pl.suzuyo.common.gui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public abstract class MyDialogWrapper<T extends MyComponent> extends DialogWrapper {

    private T component;

    protected MyDialogWrapper(String title, T component) {
        super(true);
        this.component = component;
        setTitle(title);
        setResizable(false);
        init();
    }

    protected T getComponent() {
        return component;
    }

    @Override
    protected void init() {
        component.init();
        super.init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return component.getRootPanel();
    }

    @NotNull
    @Override
    protected List<ValidationInfo> doValidateAll() {
        return component.validate();
    }
}
