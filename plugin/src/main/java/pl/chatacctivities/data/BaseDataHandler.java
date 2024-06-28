package pl.chatacctivities.data;

import pl.chatacctivities.ChatActivities;

import java.io.File;
import java.io.IOException;

public abstract class BaseDataHandler {

    protected final ChatActivities plugin = ChatActivities.getInstance();

    public abstract void loadData() throws IOException;

    protected File getFile(String fileName) {
        File file = new File(plugin.getDataFolder() + "/" + fileName);
        if(!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        return file;
    }

}
