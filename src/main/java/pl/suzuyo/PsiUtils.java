package pl.suzuyo;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.util.PsiTreeUtil;
import pl.suzuyo.generator.SelectionElement;

import java.util.ArrayList;
import java.util.List;

public class PsiUtils {

    public static void setTextForDocument(Document document, String text) {
        String newText = text == null ? "" : text;
        ApplicationManager.getApplication().runWriteAction(() -> {
            if (document.isWritable()) {
                document.setText(newText);
            } else {
                document.setReadOnly(false);
                document.setText(newText);
                document.setReadOnly(true);
            }
        });
    }

    public static List<SelectionElement> getAllSelectedElementsByType(PsiFile file, Caret caret, Class<? extends PsiElement> type) {
        List<SelectionElement> selectedElements = new ArrayList<>();
        for (Caret selectedCaret : caret.getCaretModel().getAllCarets()) {
            PsiElement selectedElement = getSelectedElement(file, selectedCaret);
            if (selectedElement != null && selectedElement.getClass() == type) {
                SelectionElement selectionElement = new SelectionElement();
                selectionElement.setPsiFile(file);
                selectionElement.setPsiClass(getSelectedElementClass(selectedElement));
                selectionElement.setPsiElement(selectedElement);
                selectedElements.add(selectionElement);
            }
        }
        return selectedElements;
    }

    public static PsiElement getSelectedElement(PsiFile file, Caret caret) {
        PsiElement selectedElement = file.findElementAt(caret.getLeadSelectionOffset());
        if (selectedElement == null) {
            throw new RuntimeException("Not found selected element");
        }
        PsiElement parent = selectedElement.getParent();
        if (parent instanceof PsiJavaCodeReferenceElement) {
            return parent.getParent();
        }
        return parent;
    }

    private static PsiClass getSelectedElementClass(PsiElement selectedElement) {
        return (selectedElement instanceof PsiClass) ? (PsiClass) selectedElement : PsiTreeUtil.getParentOfType(selectedElement, PsiClass.class);
    }
}
