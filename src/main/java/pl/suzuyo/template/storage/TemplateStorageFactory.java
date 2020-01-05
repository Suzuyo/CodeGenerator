package pl.suzuyo.template.storage;

import com.intellij.openapi.components.ServiceManager;
import pl.suzuyo.template.Template;

public class TemplateStorageFactory {

    public TemplateStorage create(Template template) {
        TemplateStorage templateStorage = new TemplateStorage();
        templateStorage.setName(template.getName());
        templateStorage.setType(template.getType());
        templateStorage.setInitScript(template.getInitScript());
        templateStorage.setScript(template.getScript());
        templateStorage.setParameters(template.getParameters());
        return templateStorage;
    }

    public static TemplateStorageFactory getInstance() {
        TemplateStorageFactory service = ServiceManager.getService(TemplateStorageFactory.class);
        if (service == null) {
            throw new RuntimeException("Cannot load template storage factory");
        }
        return service;
    }
}
