package pl.chatacctivities.data;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.HashMap;

public class Messages extends BaseDataHandler {

    private final String FILE_NAME = "messages.yml";
    private final String PREFIX_KEY = "prefix";
    private final HashMap<String, String> messages = new HashMap<>();

    @Override
    public void loadData() throws IOException {
        YamlConfiguration yml = getYaml(FILE_NAME);
        ConfigurationSection section = yml.getConfigurationSection("messages");
        if(section == null) {
            throw new IOException("Messages not found in " + FILE_NAME);
        }
        for(String key : section.getKeys(false)) {
            addMessage(key, yml.getString("messages." + key, "?"));
        }
    }

    public void addMessage(String key, String message) {
        messages.put(key, message);
    }

    public void removeMessage(String key) {
        messages.remove(key);
    }

    public String getMessage(String key) {
        return messages.get(key).replace("%" + PREFIX_KEY + "%", getPrefix());
    }

    public String getPrefix() {
        return messages.get(PREFIX_KEY);
    }

}
