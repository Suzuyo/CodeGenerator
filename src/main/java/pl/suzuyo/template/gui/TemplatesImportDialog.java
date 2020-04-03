package pl.suzuyo.template.gui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.JBColor;
import com.sun.istack.Nullable;

import javax.swing.*;
import java.awt.*;

public class TemplatesImportDialog extends DialogWrapper {

    private String templateName;

    public TemplatesImportDialog(String templateName) {
        super(true);
        setTitle("Question");
        setResizable(false);
        this.templateName = templateName;
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new FlowLayout());
        JLabel label1 = new JLabel("Template about name");
        JLabel templateLabel = new JLabel(templateName);
        templateLabel.setForeground(JBColor.RED);
        JLabel label2 = new JLabel("already exists. Do you want to replace it?");
        dialogPanel.setPreferredSize(new Dimension(0, 30));
        dialogPanel.add(label1);
        dialogPanel.add(templateLabel);
        dialogPanel.add(label2);
        return dialogPanel;
    }
}
