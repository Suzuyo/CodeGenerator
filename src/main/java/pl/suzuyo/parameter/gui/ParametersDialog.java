package pl.suzuyo.parameter.gui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kohsuke.rngom.util.Localizer;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ParametersDialog extends DialogWrapper {

    private Map<JLabel, JTextField> parameters;

    public ParametersDialog(List<String> params) {
        super(true);
        parameters = new HashMap<>();
        for (String param : params) {
            parameters.put(new JLabel(param), new JTextField());
        }
        init();
        setResizable(false);
        setTitle("Parameters");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        for (Map.Entry<JLabel, JTextField> argument : parameters.entrySet()) {
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            panel.add(argument.getKey(), gridBagConstraints);
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 1;
            gridBagConstraints1.weightx = 1;
            panel.add(argument.getValue(), gridBagConstraints1);
        }
        return panel;
    }

    public Map<String, String> showAndGetParameters() {
        super.showAndGet();
        return parameters.entrySet().stream().collect(Collectors.toMap(x -> x.getKey().getText(), y -> y.getValue().getText()));
    }

    @NotNull
    @Override
    protected List<ValidationInfo> doValidateAll() {
        List<ValidationInfo> validationInfoList = new LinkedList<>();
        for (Map.Entry<JLabel, JTextField> argument : parameters.entrySet()) {
            if (argument.getValue().getText().isEmpty()) {
                validationInfoList.add(new ValidationInfo("", argument.getValue()));
            }
        }
        return validationInfoList;
    }
}
