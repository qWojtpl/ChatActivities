package pl.chatacctivities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chatacctivities.ChatActivities;
import pl.chatacctivities.activities.Activity;
import pl.chatacctivities.data.Messages;
import pl.chatacctivities.managers.GameManager;

import java.lang.reflect.ParameterizedType;

public abstract class BaseHandler<T extends Activity> implements CommandExecutor {

    private final GameManager gameManager = ChatActivities.getInstance().getGameManager();
    private final Messages messages = ChatActivities.getInstance().getMessages();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(getMessage("mustBePlayer"));
            return true;
        }
        if(gameManager.getCurrentActivity() == null) {
            sender.sendMessage(getMessage("noActivity"));
            return true;
        }
        if(!gameManager.getCurrentActivity().getClass().getSimpleName().toLowerCase().contains(command.getName().toLowerCase())) {
            sender.sendMessage(getMessage("noActivity"));
            return true;
        }
        if(args.length == 0) {
            sendCorrectUsage(sender, label);
            return true;
        }
        if(args[0].isEmpty()) {
            sendCorrectUsage(sender, label);
            return true;
        }
        boolean completed = gameManager.getCurrentActivity().onCommand((Player) sender, String.join(" ", args));
        if(completed) {
            gameManager.stopCurrentActivity();
        } else {
            sender.sendMessage(messages.getMessage("notCorrect"));
        }
        return true;
    }

    private void sendCorrectUsage(CommandSender sender, String label) {
        sender.sendMessage(getMessage("correctUsage") + "/" + label + "<" + getMessage("answer") + ">");
    }

    private String getMessage(String key) {
        return this.messages.getMessage(key);
    }


}
