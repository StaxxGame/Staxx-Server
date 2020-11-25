package me.staxxga.server;

import me.staxxga.server.api.PluginLoader;
import me.staxxga.server.api.StaxxCommand;
import me.staxxga.server.api.StaxxPlugin;

import java.util.HashMap;
import java.util.Map;

public class StaxxServer {

    public static StaxxServer instance;

    private Map<String, StaxxCommand> commandMap;
    private PluginLoader pluginLoader;

    public static void main(String[] args) {
        new StaxxServer().init(args);
    }

    public void init(String[] args) {
        instance = this;
        this.commandMap = new HashMap<>();
        this.pluginLoader = new PluginLoader(this);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (StaxxPlugin plugin : pluginLoader.getPlugins())
                plugin.onShutdown(this);
        }));
    }

    public Map<String, StaxxCommand> getCommandMap() {
        return commandMap;
    }

    public PluginLoader getPluginLoader() {
        return pluginLoader;
    }

}
