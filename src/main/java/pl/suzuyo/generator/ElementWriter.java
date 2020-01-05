package pl.suzuyo.generator;

import com.intellij.openapi.project.Project;

abstract class ElementWriter {
    Project project;
    String script;

    ElementWriter(String script, Project project) {
        this.script = script;
        this.project = project;
    }

    abstract void write();
}
