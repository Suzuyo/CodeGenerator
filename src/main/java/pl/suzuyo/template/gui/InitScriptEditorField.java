package pl.suzuyo.template.gui;

import com.intellij.codeInsight.AutoPopupController;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.SpellCheckingEditorCustomizationProvider;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.ui.EditorCustomization;
import com.intellij.ui.LanguageTextField;
import com.intellij.util.textCompletion.TextCompletionUtil;

public class InitScriptEditorField extends LanguageTextField {

    public InitScriptEditorField() {
        super(Language.findLanguageByID("Groovy"), ProjectManager.getInstance().getDefaultProject(), "");
    }

    @Override
    protected EditorEx createEditor() {
        EditorEx editor = (EditorEx) EditorFactory.getInstance().createEditor(getDocument(), getProject());
        final EditorSettings settings = editor.getSettings();
        settings.setLineNumbersShown(true);
        settings.setFoldingOutlineShown(true);
        settings.setLineMarkerAreaShown(true);
        editor.setHorizontalScrollbarVisible(true);
        editor.setVerticalScrollbarVisible(true);
        settings.setAdditionalPageAtBottom(true);
        settings.setLineCursorWidth(1);
        EditorCustomization disableSpellChecking = SpellCheckingEditorCustomizationProvider.getInstance().getDisabledCustomization();
        if (disableSpellChecking != null) disableSpellChecking.customize(editor);
        editor.putUserData(AutoPopupController.ALWAYS_AUTO_POPUP, true);
        TextCompletionUtil.installCompletionHint(editor);
        return editor;
    }
}
