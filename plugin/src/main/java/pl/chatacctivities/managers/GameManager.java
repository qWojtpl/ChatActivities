package pl.chatacctivities.managers;

import pl.chatacctivities.ChatActivities;
import pl.chatacctivities.activities.Activity;
import pl.chatacctivities.data.ConfigData;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {

    private final ChatActivities plugin = ChatActivities.getInstance();
    private final List<String> rewards = new ArrayList<>();
    private Activity currentActivity;
    private int task;

    public Activity getCurrentActivity() {
        return this.currentActivity;
    }

    public void startActivity(Activity activity) {
        if(currentActivity != null) {
            throw new RuntimeException("Activity " + activity.getClass().getSimpleName() + " is already running!");
        }
        sendConsoleMessage("Started activity: " + activity.getClass().getSimpleName());
        currentActivity = activity;
        activity.onStart();
    }

    public Activity startActivity(Class<? extends Activity> activity) {
        try {
            Constructor<?> constructor = activity.getConstructor();
            Activity activityInstance = (Activity) constructor.newInstance();
            startActivity(activityInstance);
            return activityInstance;
        } catch(Exception exception) {
            plugin.getLogger().severe("Cannot start activity by class: " + exception.getMessage());
        }
        return null;
    }

    public void stopActivity(Activity activity) {
        sendConsoleMessage("Stopped activity: " + activity.getClass().getSimpleName());
        currentActivity = null;
        activity.onStop();
    }

    public void stopCurrentActivity() {
        if(getCurrentActivity() == null) {
            return;
        }
        stopActivity(getCurrentActivity());
    }

    public void addReward(String reward) {
        rewards.add(reward);
    }

    public void removeReward(String reward) {
        rewards.remove(reward);
    }

    public List<String> getRewards() {
        return new ArrayList<>(rewards);
    }

    public void startEventInterval() {
        ConfigData configData = plugin.getConfigData();
        task = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            stopCurrentActivity();
            int randomIndex = new Random().nextInt(configData.getActivities().size());
            String randomActivity = configData.getActivities().get(randomIndex);
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "ca start " + randomActivity);
        }, 20L * 60 * configData.getEventInterval(), 20L * 60 * configData.getEventInterval());
    }

    public void stopEventInterval() {
        plugin.getServer().getScheduler().cancelTask(task);
    }

    private void sendConsoleMessage(String message) {
        plugin.getServer().getConsoleSender().sendMessage(message);
    }

}
