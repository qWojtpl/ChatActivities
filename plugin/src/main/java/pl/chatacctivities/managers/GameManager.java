package pl.chatacctivities.managers;

import pl.chatacctivities.ChatActivities;
import pl.chatacctivities.activities.Activity;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private final ChatActivities plugin = ChatActivities.getInstance();
    private final List<Activity> currentActivities = new ArrayList<>();

    public void startActivity(Activity activity) {
        if(hasActivity(activity.getClass())) {
            throw new RuntimeException("Activity " + activity.getClass().getSimpleName() + " is already running!");
        }
        sendConsoleMessage("Started activity: " + activity.getClass().getSimpleName());
        currentActivities.add(activity);
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
        currentActivities.remove(activity);
        activity.onStop();
    }

    public void stopAllActivities() {
        List<Activity> currentActivities = new ArrayList<>(this.currentActivities);
        for(Activity activity : currentActivities) {
            stopActivity(activity);
        }
    }

    private boolean hasActivity(Class<? extends Activity> activity) {
        for(Activity a : this.currentActivities) {
            if(a.getClass().equals(activity)) {
                return true;
            }
        }
        return false;
    }

    private void sendConsoleMessage(String message) {
        plugin.getServer().getConsoleSender().sendMessage(message);
    }

}
