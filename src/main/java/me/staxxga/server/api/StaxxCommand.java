package me.staxxga.server.api;

public abstract class StaxxCommand {

    private final String name;
    private final String description;
    private final String[] aliases;

    public StaxxCommand(String name, String description, String... aliases) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
    }

    public abstract void execute(String aliasUsed, CommandSender sender, String[] args);

}
