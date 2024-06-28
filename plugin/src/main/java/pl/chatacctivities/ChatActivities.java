package pl.chatacctivities;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import pl.chatacctivities.activities.CodeActivity;
import pl.chatacctivities.activities.JigsawActivity;
import pl.chatacctivities.commands.BaseHandler;
import pl.chatacctivities.commands.CodeHandler;
import pl.chatacctivities.commands.JigsawHandler;
import pl.chatacctivities.data.BaseDataHandler;
import pl.chatacctivities.data.DictionaryData;
import pl.chatacctivities.managers.GameManager;

import java.io.IOException;

public final class ChatActivities extends JavaPlugin {

    private static ChatActivities instance;
    private GameManager gameManager;
    private DictionaryData dictionaryData;

    // Activity commands
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
        getLogger().info("Activities disabled.");
    }

    private void initializeInstances() {
        this.gameManager = new GameManager();
        this.dictionaryData = new DictionaryData();
        loadData(dictionaryData);

        // Activity commands
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

    public DictionaryData getDictionaryData() {
        return this.dictionaryData;
    }

    public JigsawHandler<?> getJigsawHandler() {
        return this.jigsawHandler;
    }

    public CodeHandler<?> getCodeHandler() {
        return this.codeHandler;
    }

}
