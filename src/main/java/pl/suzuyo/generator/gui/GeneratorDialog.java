package pl.suzuyo.generator.gui;

import pl.suzuyo.common.gui.MyDialogWrapper;
import pl.suzuyo.template.Template;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class GeneratorDialog extends MyDialogWrapper<GeneratorComponent> {

    public GeneratorDialog() {
        super("Script Generator", new GeneratorComponent());
    }

    @Override
    protected void init() {
        KeyStroke downKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        getRootPane().registerKeyboardAction(event -> getComponent().activeTemplateComboBox(), downKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        super.init();
    }

    public Template showAndGetTemplate() {
        return showAndGet() ? getComponent().getSelectedTemplate() : null;
    }
}
