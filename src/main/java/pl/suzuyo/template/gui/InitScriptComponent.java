package pl.suzuyo.template.gui;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.Key;
import com.intellij.ui.EditorTextField;
import pl.suzuyo.PsiUtils;
import pl.suzuyo.common.gui.MyComponent;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class InitScriptComponent extends MyComponent {
    public static final Key<String[]> COMPLETE_VARIABLES_KEY = new Key<>("COMPLETE_VARIABLES");
    private JPanel rootPanel;
    private Document initScriptDocument;
    private JPanel initScriptEditorPanel;

    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }

    void setScript(String script) {
        PsiUtils.setTextForDocument(initScriptDocument, script);
    }

    String getScript() {
        return initScriptDocument.getText();
    }

    void setCompleteVariables(Set<String> variables) {
        initScriptDocument.putUserData(COMPLETE_VARIABLES_KEY, variables.toArray(new String[]{}));
    }

    void clearFieldsValues() {
        PsiUtils.setTextForDocument(initScriptDocument, "");
    }

    void setFieldsAsEditable(boolean editable) {
        initScriptDocument.setReadOnly(!editable);
    }

    private void createUIComponents() {
        initScriptEditorPanel = new JPanel();
        initScriptEditorPanel.setLayout(new GridBagLayout());
        EditorTextField editorTextField = new InitScriptEditorField();
        initScriptDocument = editorTextField.getDocument();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        initScriptEditorPanel.add(editorTextField.getComponent(), gridBagConstraints);
    }
}
