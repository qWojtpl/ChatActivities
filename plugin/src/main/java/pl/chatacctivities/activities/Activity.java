package pl.chatacctivities.activities;

import org.bukkit.entity.Player;
import pl.chatacctivities.ChatActivities;
import pl.chatacctivities.data.DictionaryData;
import pl.chatacctivities.data.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Activity {

    protected final ChatActivities plugin = ChatActivities.getInstance();
    protected final DictionaryData dictionaryData = plugin.getDictionaryData();
    protected final Messages messages = plugin.getMessages();

    private final String colors = "0123456789aecbdf";

    public abstract void onStart();
    public abstract boolean onCommand(Player player, String argument);
    public abstract void onStop();

    protected String getRandomWord() {
        List<String> words = new ArrayList<>(dictionaryData.getWords());
        Random random = new Random();
        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }

    protected String getRandomColor() {
        Random random = new Random();
        String[] colors = this.colors.split("");
        int randomIndex = random.nextInt(colors.length);
        return "§" + colors[randomIndex];
    }

    protected void broadcastMessage(String message) {
        plugin.getServer().getConsoleSender().sendMessage("[ChatActivities Broadcast Log] " + message);
        for(Player p : plugin.getServer().getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }

}
