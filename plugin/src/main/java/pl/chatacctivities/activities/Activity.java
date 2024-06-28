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

    private final String COLORS = "123456789abcdef";

    public abstract void onStart();
    public abstract boolean onCommand(Player player, String argument);
    public abstract void onStop();

    protected String getRandomWord() {
        List<String> words = new ArrayList<>(dictionaryData.getWords());
        return words.get(getRandomNumber(words.size()));
    }

    protected String getRandomColor() {
        String[] colors = COLORS.split("");
        return "§" + colors[getRandomNumber(colors.length)];
    }

    protected String invokeRandomReward(Player player) {
        List<String> rewards = plugin.getGameManager().getRewards();
        String reward = rewards.get(getRandomNumber(rewards.size()));
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), String.format(reward, player.getName()));
        return reward;
    }

    protected void broadcastMessage(String message) {
        plugin.getServer().getConsoleSender().sendMessage("[ChatActivities Broadcast Log] " + message);
        for(Player p : plugin.getServer().getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }

    protected void stopActivity() {
        plugin.getGameManager().stopActivity(this);
    }

    protected int getRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

}
