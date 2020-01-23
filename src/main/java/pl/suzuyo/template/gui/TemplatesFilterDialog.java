package pl.suzuyo.template.gui;

import pl.suzuyo.common.gui.MyDialogWrapper;
import pl.suzuyo.template.Template;
import java.util.Set;

public class TemplatesFilterDialog extends MyDialogWrapper<TemplatesFilterComponent> {

    protected TemplatesFilterDialog() {
        super("Templates filter", new TemplatesFilterComponent());
    }

    public void setTemplates(Set<Template> templates) {
        getComponent().setTemplates(templates);
    }

    public void setDescription(String description) {
        getComponent().setDescription(description);
    }

    public Set<Template> showAndGetTemplates() {
        super.showAndGet();
        return getComponent().getTemplates();
    }
}
