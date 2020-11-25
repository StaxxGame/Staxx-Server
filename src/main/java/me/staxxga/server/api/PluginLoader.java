package me.staxxga.server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.staxxga.server.StaxxServer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader {

    private StaxxServer server;
    private List<StaxxPlugin> plugins;

    public PluginLoader(StaxxServer server) {
        this.server = server;
        this.plugins = new ArrayList<>();
        for (File file : new File("plugins/").listFiles()) {
            try {
                JarFile jar = new JarFile(file);
                JarEntry entry = jar.getJarEntry("plugin.json");

                InputStream stream = jar.getInputStream(entry);
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> jsonMap = mapper.readValue(stream, Map.class);
                if (!jsonMap.containsKey("main")) {
                    System.out.println("No main class found in plugin.json of " + file.getName());
                    continue;
                }
                Class<? extends StaxxPlugin> pluginClass = (Class<? extends StaxxPlugin>) Class.forName(jsonMap.get("main").toString(), true, new URLClassLoader(new URL[]{file.toURI().toURL()}));
                plugins.add(pluginClass.getConstructor().newInstance());
            } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                System.out.println("There was an error loading " + file.getName());
                e.printStackTrace();
            }
        }
        for (StaxxPlugin plugin : plugins)
            plugin.onStartup(server);
    }

    public List<StaxxPlugin> getPlugins() {
        return plugins;
    }

}
