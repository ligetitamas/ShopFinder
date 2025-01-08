package me.ltommi.shopfinder;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.olziedev.playerwarps.api.PlayerWarpsAPI;
import me.ltommi.shopfinder.commands.FindShop;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;

public final class Main extends JavaPlugin {
    private FileConfiguration config;
    private FileConfiguration messages;
    private QuickShopAPI qsapi;
    private PlayerWarpsAPI pwapi;
    @Override
    public void onEnable() {
        LoadConfig();
        Plugin plugin = Bukkit.getPluginManager().getPlugin("QuickShop");
        if(plugin != null){
            qsapi= (QuickShopAPI)plugin;
        }
        plugin = Bukkit.getPluginManager().getPlugin("PlayerWarps");
        if(plugin != null){
            pwapi= PlayerWarpsAPI.getInstance();
        }
        getCommand("findshop").setExecutor(new FindShop(messages, config, qsapi,pwapi));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void LoadConfig(){
        File config =new File(this.getDataFolder(),"config.yml");
        if (!config.exists()){
            this.saveDefaultConfig();
        }

        File messages=new File(this.getDataFolder(),"messages.yml");
        if (!messages.exists()){
            this.saveResource("messages.yml",false);
        }
        this.config= YamlConfiguration.loadConfiguration(config);
        this.messages= YamlConfiguration.loadConfiguration(messages);
        Log("Configuration loaded");
    }

    public void Log(String log){
        this.getLogger().info(log);
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }
}
