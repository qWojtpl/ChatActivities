package pl.chatacctivities;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import pl.chatacctivities.activities.CodeActivity;
import pl.chatacctivities.activities.JigsawActivity;
import pl.chatacctivities.commands.BaseHandler;
import pl.chatacctivities.commands.CodeHandler;
import pl.chatacctivities.commands.JigsawHandler;
import pl.chatacctivities.managers.GameManager;

public final class ChatActivities extends JavaPlugin {

    private static ChatActivities instance;
    private GameManager gameManager;
    private JigsawHandler<?> jigsawHandler;
    private CodeHandler<?> codeHandler;

    @Override
    public void onEnable() {
        instance = this;
        initializeInstances();
        initializeCommands();
        getLogger().info("Activities enabled.");
    }

    @Override
    public void onDisable() {
        gameManager.stopAllActivities();
        getLogger().info("Activities disabled");
    }

    private void initializeInstances() {
        this.gameManager = new GameManager();
        this.jigsawHandler = new JigsawHandler<JigsawActivity>();
        this.codeHandler = new CodeHandler<CodeActivity>();
    }

    private void initializeCommands() {
        initializeCommand("code", codeHandler);
        initializeCommand("jigsaw", jigsawHandler);
    }

    private void initializeCommand(String name, BaseHandler<?> handler) {
        PluginCommand command = getCommand(name);
        if(command != null) {
            command.setExecutor(handler);
        }
    }

    public static ChatActivities getInstance() {
        return instance;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public JigsawHandler<?> getJigsawHandler() {
        return this.jigsawHandler;
    }

    public CodeHandler<?> getCodeHandler() {
        return this.codeHandler;
    }

}
