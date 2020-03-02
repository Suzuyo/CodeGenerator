package pl.suzuyo.template.gui;

import pl.suzuyo.storage.StorageReader;
import pl.suzuyo.storage.StorageWriter;
import pl.suzuyo.common.gui.OperatedListPanel;
import pl.suzuyo.template.Template;

public class TemplateListPanel extends OperatedListPanel<Template> {

    private boolean checkIndex = false;

    public void loadFromStorage() {
        setItems(StorageReader.getInstance().loadTemplates());
        if (!checkIndex) {
            selectFirstOne();
        } else {
            selectLastIndex();
        }
    }

    public void saveToStorage() {
        checkIndex = true;
        StorageWriter.getInstance().saveTemplates(getItems());
    }

    @Override
    protected Template createElement() {
        return new NewTemplateDialog().showAndGetTemplate();
    }
}
