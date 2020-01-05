package pl.suzuyo.template;

public enum TemplateType {
    CLASS("Class"),
    METHOD("Method"),
    ANNOTATION("Annotation");

    private final String name;

    TemplateType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
