package pl.suzuyo.storage;

import com.intellij.openapi.components.ServiceManager;
import pl.suzuyo.NotFoundService;
import pl.suzuyo.template.Template;
import pl.suzuyo.template.storage.TemplateStorage;
import pl.suzuyo.template.storage.TemplateStorageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class StorageWriter {

    public void saveTemplates(List<Template> templates) {
        TemplateStorageFactory templateStorageFactory = TemplateStorageFactory.getInstance();
        List<TemplateStorage> templatesStorage = templates.stream()
                .map(templateStorageFactory::create)
                .collect(Collectors.toList());
        StorageService.getInstance().setTemplatesStorage(templatesStorage);
    }

    public static StorageWriter getInstance() {
        StorageWriter storageWriter = ServiceManager.getService(StorageWriter.class);
        if (storageWriter == null) {
            throw new NotFoundService("Cannot load storage writer");
        }
        return storageWriter;
    }
}
