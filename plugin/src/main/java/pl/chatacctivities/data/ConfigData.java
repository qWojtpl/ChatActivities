package pl.chatacctivities.data;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.List;

public class ConfigData extends BaseDataHandler {

    private String adminPermission;

    @Override
    public void loadData() throws IOException {
        YamlConfiguration yml = getYaml("config.yml");
        this.adminPermission = yml.getString("config.adminPermission", "chatactivities.manage");
        List<String> rewards = yml.getStringList("config.rewards");
        for(String reward : rewards) {
            plugin.getGameManager().addReward(reward);
        }
    }

    public String getAdminPermission() {
        return this.adminPermission;
    }

}
