package pl.suzuyo.template.gui;

import pl.suzuyo.common.gui.MyDialogWrapper;
import pl.suzuyo.template.Template;

import javax.swing.*;

class NewTemplateDialog extends MyDialogWrapper<NewTemplateComponent> {

    NewTemplateDialog() {
        super("New Template", new NewTemplateComponent());
    }

    @Override
    protected void init() {
        setButtonsAlignment(SwingConstants.CENTER);
        super.init();
    }

    Template showAndGetTemplate() {
        if (super.showAndGet()) {
            return getComponent().createTemplate();
        }
        return null;
    }
}
