package pl.chatacctivities;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import pl.chatacctivities.activities.CodeActivity;
import pl.chatacctivities.activities.JigsawActivity;
import pl.chatacctivities.commands.ActivityStarter;
import pl.chatacctivities.commands.BaseHandler;
import pl.chatacctivities.commands.CodeHandler;
import pl.chatacctivities.commands.JigsawHandler;
import pl.chatacctivities.data.BaseDataHandler;
import pl.chatacctivities.data.ConfigData;
import pl.chatacctivities.data.DictionaryData;
import pl.chatacctivities.data.Messages;
import pl.chatacctivities.managers.GameManager;

import java.io.IOException;

public final class ChatActivities extends JavaPlugin {

    private static ChatActivities instance;
    private GameManager gameManager;
    private ConfigData configData;
    private DictionaryData dictionaryData;
    private Messages messages;

    // Activity commands
    private JigsawHandler<JigsawActivity> jigsawHandler;
    private CodeHandler<CodeActivity> codeHandler;

    private ActivityStarter activityStarter;

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
        getLogger().info("Activities disabled.");
    }

    private void initializeInstances() {
        this.gameManager = new GameManager();
        this.configData = new ConfigData();
        this.dictionaryData = new DictionaryData();
        this.messages = new Messages();

        loadData(configData);
        loadData(dictionaryData);
        loadData(messages);

        // Activity commands
        this.jigsawHandler = new JigsawHandler<>();
        this.codeHandler = new CodeHandler<>();

        this.activityStarter = new ActivityStarter();
    }

    private void initializeCommands() {
        initializeCommand("code", codeHandler);
        initializeCommand("jigsaw", jigsawHandler);
        PluginCommand command = getCommand("chatactivities");
        if(command != null) {
            command.setExecutor(activityStarter);
        }
    }

    private void initializeCommand(String name, BaseHandler<?> handler) {
        PluginCommand command = getCommand(name);
        if(command != null) {
            command.setExecutor(handler);
        }
    }

    private void loadData(BaseDataHandler dataHandler) {
        try {
            dataHandler.loadData();
        } catch(IOException e) {
            getLogger().severe("Cannot load " + dataHandler.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public static ChatActivities getInstance() {
        return instance;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public ConfigData getConfigData() {
        return this.configData;
    }

    public DictionaryData getDictionaryData() {
        return this.dictionaryData;
    }

    public Messages getMessages() {
        return this.messages;
    }

    public JigsawHandler<JigsawActivity> getJigsawHandler() {
        return this.jigsawHandler;
    }

    public CodeHandler<CodeActivity> getCodeHandler() {
        return this.codeHandler;
    }

    public ActivityStarter getActivityStarter() {
        return this.activityStarter;
    }

}
