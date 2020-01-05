package pl.suzuyo.template;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Template {
    private String name;
    private TemplateType type;
    private String initScript;
    private String script;
    private List<String> parameters = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TemplateType getType() {
        return type;
    }

    public void setType(TemplateType type) {
        this.type = type;
    }

    public String getInitScript() {
        return initScript;
    }

    public void setInitScript(String initScript) {
        this.initScript = initScript;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void addParameter(String parameter) {
        this.parameters.add(parameter);
    }

    public void removeParameter(String parameterName) {
        this.parameters.remove(parameterName);
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public Set<String> getVariables() {
        ScriptService scriptService = ScriptService.getInstance();
        return scriptService.getVariablesNames(script);
    }

    @JsonIgnore
    public Set<String> getVariablesWithoutParameters() {
        return getVariables().stream()
                .filter(variablesName -> parameters.stream().noneMatch(variablesName::equals))
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template that = (Template) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
