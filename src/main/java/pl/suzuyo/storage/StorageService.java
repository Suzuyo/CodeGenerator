package pl.suzuyo.storage;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.suzuyo.NotFoundService;
import pl.suzuyo.template.storage.TemplateStorage;

import java.util.List;

@State(
        name = "StorageService",
        storages = {
                @Storage("StorageService.xml")
        })
public class StorageService implements PersistentStateComponent<StorageService> {
    private List<TemplateStorage> templatesStorage = ContainerUtil.createConcurrentList();

    public List<TemplateStorage> getTemplatesStorage() {
        return templatesStorage;
    }

    public void setTemplatesStorage(List<TemplateStorage> templatesStorage) {
        this.templatesStorage = templatesStorage;
    }

    @Nullable
    @Override
    public StorageService getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull StorageService state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public static StorageService getInstance() {
        StorageService service = ServiceManager.getService(StorageService.class);
        if (service == null) {
            throw new NotFoundService("Cannot load storage service");
        }
        return service;
    }
}
