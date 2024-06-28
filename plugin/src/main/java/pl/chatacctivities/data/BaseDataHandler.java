package pl.chatacctivities.data;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.chatacctivities.ChatActivities;

import java.io.File;
import java.io.IOException;

public abstract class BaseDataHandler {

    protected final ChatActivities plugin = ChatActivities.getInstance();

    public abstract void loadData() throws IOException;

    protected final YamlConfiguration getYaml(String fileName) {
        return YamlConfiguration.loadConfiguration(getFile(fileName));
    }

    protected final File getFile(String fileName) {
        File file = new File(plugin.getDataFolder() + "/" + fileName);
        if(!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        return file;
    }

}
