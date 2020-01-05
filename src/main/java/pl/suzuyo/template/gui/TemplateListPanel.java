package pl.suzuyo.template.gui;

import pl.suzuyo.storage.StorageReader;
import pl.suzuyo.storage.StorageWriter;
import pl.suzuyo.common.gui.OperatedListPanel;
import pl.suzuyo.template.Template;

public class TemplateListPanel extends OperatedListPanel<Template> {

    public void loadFromStorage() {
        setItems(StorageReader.getInstance().loadTemplates());
        selectFirstOne();
    }

    public void saveToStorage() {
        StorageWriter.getInstance().saveTemplates(getItems());
    }

    @Override
    protected Template createElement() {
        return new NewTemplateDialog().showAndGetTemplate();
    }
}
