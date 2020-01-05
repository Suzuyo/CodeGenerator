package pl.suzuyo.template;

import com.intellij.openapi.components.ServiceManager;
import pl.suzuyo.template.storage.TemplateStorage;

import java.util.Optional;

public class TemplateFactory {

    public Template create(TemplateStorage templateStorage) {
        Template template = new Template();
        template.setName(templateStorage.getName());
        template.setType(templateStorage.getType());
        template.setInitScript(templateStorage.getInitScript());
        template.setScript(templateStorage.getScript());
        Optional.ofNullable(templateStorage.getParameters()).ifPresent(template::setParameters);
        return template;
    }

    public static TemplateFactory getInstance() {
        TemplateFactory service = ServiceManager.getService(TemplateFactory.class);
        if (service == null) {
            throw new RuntimeException("Cannot load template factory");
        }
        return service;
    }
}
