package pl.suzuyo.storage;

import com.intellij.openapi.components.ServiceManager;
import pl.suzuyo.NotFoundService;
import pl.suzuyo.template.Template;
import pl.suzuyo.template.TemplateFactory;

import java.util.List;
import java.util.stream.Collectors;

public class StorageReader {

    public List<Template> loadTemplates() {
        TemplateFactory templateFactory = TemplateFactory.getInstance();
        return StorageService.getInstance().getTemplatesStorage().stream()
                .map(templateFactory::create)
                .collect(Collectors.toList());
    }

    public static StorageReader getInstance() {
        StorageReader storageReader = ServiceManager.getService(StorageReader.class);
        if (storageReader == null) {
            throw new NotFoundService("Cannot load storage reader");
        }
        return storageReader;
    }
}
