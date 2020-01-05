package pl.suzuyo.template.gui;

import pl.suzuyo.storage.StorageReader;
import pl.suzuyo.common.gui.MyComboBox;
import pl.suzuyo.template.Template;

public class TemplateComboBox extends MyComboBox<Template> {

    public void loadFromStorage() {
        setItems(StorageReader.getInstance().loadTemplates());
    }
}
