package me.staxxga.server.api;

import me.staxxga.server.StaxxServer;

public abstract class StaxxPlugin {

    private final String name;
    private final String description;
    private final String version;
    private final String[] authors;

    public StaxxPlugin(String name, String description, String version, String... authors) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.authors = authors;
    }

    public abstract void onStartup(StaxxServer server);

    public abstract void onShutdown(StaxxServer server);

}
