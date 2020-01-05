package pl.suzuyo.template;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupElementPresentation;
import com.intellij.codeInsight.lookup.LookupElementRenderer;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.annotations.NotNull;
import pl.suzuyo.template.gui.InitScriptComponent;

public class InitScriptContributor extends CompletionContributor {

    private static final String VARIABLES_PREFIX = "v:";

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        if (parameters.getCompletionType().equals(CompletionType.BASIC)) {
            if (acceptConstElements(parameters)) {
                addConstElements(result);
            }
            if (acceptVariableElements(parameters, result)) {
                String[] variables = parameters.getEditor().getDocument().getUserData(InitScriptComponent.COMPLETE_VARIABLES_KEY);
                if (variables != null) {
                    for (String name : variables) {
                        LookupElementBuilder lookupElementBuilder = LookupElementBuilder.create(VARIABLES_PREFIX + name)
                                .withRenderer(new VariableLookupElementRenderer())
                                .withInsertHandler(new VariableInsertHandler());
                        result.addElement(lookupElementBuilder);
                    }
                }
            }
        }
    }

    private boolean acceptConstElements(@NotNull CompletionParameters parameters) {
        return !PlatformPatterns.psiElement().afterLeaf(".", "[", "\"").accepts(parameters.getPosition());
    }

    private void addConstElements(@NotNull CompletionResultSet result) {
        result.addElement(LookupElementBuilder.create("activeFile"));
        result.addElement(LookupElementBuilder.create("activeClass"));
        result.addElement(LookupElementBuilder.create("selectedField"));
        result.addElement(LookupElementBuilder.create("selectedType"));
        result.addElement(LookupElementBuilder.create("selectedParameter"));
        result.addElement(LookupElementBuilder.create("selectedLiteral"));
        result.addElement(LookupElementBuilder.create("parameters"));
        result.addElement(LookupElementBuilder.create("variables"));
    }

    private boolean acceptVariableElements(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        PsiElementPattern.Capture<LeafPsiElement> leafPsiElementCapture = PlatformPatterns.psiElement(LeafPsiElement.class);
        String prefix = result.getPrefixMatcher().getPrefix();
        return leafPsiElementCapture.accepts(parameters.getPosition()) && prefix.equals(VARIABLES_PREFIX);
    }

    class VariableLookupElementRenderer extends LookupElementRenderer<LookupElement> {

        @Override
        public void renderElement(LookupElement element, LookupElementPresentation presentation) {
            presentation.setItemText(element.getLookupString().substring(2));
        }
    }

    class VariableInsertHandler implements InsertHandler<LookupElement> {

        @Override
        public void handleInsert(@NotNull InsertionContext context, @NotNull LookupElement item) {
            String text = context.getDocument().getText();
            String newText = text.substring(0, context.getStartOffset()) + text.substring(context.getStartOffset() + 2);
            context.getDocument().setText(newText);
        }
    }
}
