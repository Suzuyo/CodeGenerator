package pl.suzuyo.template.storage;

import org.jetbrains.annotations.NotNull;
import pl.suzuyo.template.TemplateType;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class TemplateStorage implements Serializable, Comparable<TemplateStorage> {
    private String name;
    private TemplateType type;
    private String initScript;
    private String script;
    private List<String> parameters;

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

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateStorage that = (TemplateStorage) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(@NotNull TemplateStorage o) {
        return name.compareTo(o.name);
    }
}
