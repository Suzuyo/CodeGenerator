package pl.suzuyo.template.gui;

import pl.suzuyo.common.gui.MyDialogWrapper;

public class TemplatesDialog extends MyDialogWrapper<TemplatesComponent> {

    public TemplatesDialog() {
        super("Templates", new TemplatesComponent());
    }
}
