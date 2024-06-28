package pl.chatacctivities.activities;

import org.bukkit.entity.Player;
import pl.chatacctivities.ChatActivities;

public abstract class Activity {

    protected final ChatActivities plugin = ChatActivities.getInstance();

    public abstract void onStart();
    public abstract void onCommand(String argument);
    public abstract void onStop();

    protected void broadcastMessage(String message) {
        message = message.replace("&", "§");
        plugin.getServer().getConsoleSender().sendMessage("[ChatActivities Broadcast Log] " + message);
        for(Player p : plugin.getServer().getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }

}
