package pl.chatacctivities.data;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigData extends BaseDataHandler {

    private String adminPermission;
    private List<String> activities;
    private int eventInterval;

    @Override
    public void loadData() throws IOException {
        YamlConfiguration yml = getYaml("config.yml");
        this.adminPermission = yml.getString("config.adminPermission", "chatactivities.manage");
        this.eventInterval = yml.getInt("config.eventInterval", 5);
        this.activities = yml.getStringList("config.activities");
        List<String> rewards = yml.getStringList("config.rewards");
        for(String reward : rewards) {
            plugin.getGameManager().addReward(reward);
        }
    }

    public String getAdminPermission() {
        return this.adminPermission;
    }

    public List<String> getActivities() {
        return new ArrayList<>(this.activities);
    }

    public int getEventInterval() {
        return this.eventInterval;
    }

}
