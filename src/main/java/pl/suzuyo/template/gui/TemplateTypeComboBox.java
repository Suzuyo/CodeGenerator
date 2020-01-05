package pl.suzuyo.template.gui;

import pl.suzuyo.common.gui.MyComboBox;
import pl.suzuyo.template.TemplateType;

import java.util.Arrays;

public class TemplateTypeComboBox extends MyComboBox<TemplateType> {

    public void loadValues() {
        setItems(Arrays.asList(TemplateType.values()));
    }
}
