package pl.chatacctivities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chatacctivities.ChatActivities;
import pl.chatacctivities.activities.CodeActivity;
import pl.chatacctivities.activities.JigsawActivity;
import pl.chatacctivities.data.ConfigData;
import pl.chatacctivities.data.Messages;
import pl.chatacctivities.managers.GameManager;

public class ActivityStarter implements CommandExecutor {

    private final ChatActivities plugin = ChatActivities.getInstance();
    private final Messages messages = plugin.getMessages();
    private final ConfigData configData = plugin.getConfigData();
    private final GameManager gameManager = plugin.getGameManager();
    private final CodeHandler<CodeActivity> codeHandler = plugin.getCodeHandler();
    private final JigsawHandler<JigsawActivity> jigsawHandler = plugin.getJigsawHandler();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(!sender.hasPermission(configData.getAdminPermission())) {
                sender.sendMessage(messages.getMessage("noPermission"));
                return true;
            }
        }
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("info")) {
                //todo
            }
            if(args[0].equalsIgnoreCase("start")) {
                if(args.length > 1) {
                    switch(args[1].toLowerCase()) {
                        case "code":
                            CodeActivity codeActivity = (CodeActivity) gameManager.startActivity(CodeActivity.class);
                            if(codeActivity != null) {
                                codeHandler.setCurrentActivity(codeActivity);
                            }
                            break;
                        case "jigsaw":
                            JigsawActivity jigsawActivity = (JigsawActivity) gameManager.startActivity(JigsawActivity.class);
                            if(jigsawActivity != null) {
                                jigsawHandler.setCurrentActivity(jigsawActivity);
                            }
                            break;
                        default:
                            sender.sendMessage(messages.getMessage("notFound"));
                            break;
                    }
                } else {
                    sendCorrectUsage(sender);
                }
                return true;
            }
        }
        sendCorrectUsage(sender);
        return true;
    }

    private void sendCorrectUsage(CommandSender sender) {
        sender.sendMessage(messages.getMessage("help").split("%nl%"));
    }

}
