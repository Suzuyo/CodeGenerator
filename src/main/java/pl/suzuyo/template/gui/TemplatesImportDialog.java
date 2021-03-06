package pl.suzuyo.template.gui;

import com.intellij.openapi.ui.DialogWrapper;
import com.sun.istack.Nullable;

import javax.swing.*;
import java.awt.*;

public class TemplatesImportDialog extends DialogWrapper {

    private String text;

    public TemplatesImportDialog(String text) {
        super(true);
        setTitle("Question");
        setResizable(false);
        this.text = text;
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(0, 40));
        dialogPanel.add(label, BorderLayout.CENTER);
        return dialogPanel;
    }
}
