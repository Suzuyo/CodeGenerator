package pl.suzuyo.parameter.gui;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
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
}
