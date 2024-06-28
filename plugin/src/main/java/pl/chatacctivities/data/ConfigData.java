package pl.chatacctivities.data;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class ConfigData extends BaseDataHandler {

    private String adminPermission;

    @Override
    public void loadData() throws IOException {
        YamlConfiguration yml = getYaml("config.yml");
        this.adminPermission = yml.getString("config.adminPermission", "chatactivities.manage");
    }

    public String getAdminPermission() {
        return this.adminPermission;
    }

}
