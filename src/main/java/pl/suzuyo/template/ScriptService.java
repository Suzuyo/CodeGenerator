package pl.suzuyo.template;

import com.intellij.openapi.components.ServiceManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptService {

    public Set<String> getVariablesNames(String script) {
        if (script == null) {
            return Collections.emptySet();
        }
        Set<String> variablesNames = new HashSet<>();
        Pattern variablesNamesPattern = Pattern.compile("\\$\\{[A-Z_]*}");
        Matcher variablesNamesMatcher = variablesNamesPattern.matcher(script);
        while (variablesNamesMatcher.find()) {
            String variableName = variablesNamesMatcher.group();
            variablesNames.add(variableName.substring(2, variableName.length() - 1));

        }
        return variablesNames;
    }

    public String setVariables(String script, Map<String, String> variables) {
        String newScript = script;
        for (Map.Entry<String, String> variable : variables.entrySet()) {
            newScript = newScript.replaceAll("\\$\\{" + variable.getKey() + "}", variable.getValue());
        }
        return newScript;
    }

    public static ScriptService getInstance() {
        ScriptService scriptService = ServiceManager.getService(ScriptService.class);
        if (scriptService == null) {
            throw new RuntimeException("Cannot load script service");
        }
        return scriptService;
    }
}
