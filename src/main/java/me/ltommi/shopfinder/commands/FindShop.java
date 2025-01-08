package me.ltommi.shopfinder.commands;

import com.ghostchu.quickshop.api.QuickShopAPI;
import com.ghostchu.quickshop.api.shop.Shop;
import com.olziedev.playerwarps.api.PlayerWarpsAPI;
import com.olziedev.playerwarps.api.warp.Warp;
import me.ltommi.shopfinder.utils.FilteredShops;
import me.ltommi.shopfinder.utils.FindShopGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class FindShop implements CommandExecutor {
    private FileConfiguration messages;
    private FileConfiguration config;
    private QuickShopAPI qsapi;
    private PlayerWarpsAPI pwapi;
    public FindShop(FileConfiguration messages,FileConfiguration config, QuickShopAPI qsapi,PlayerWarpsAPI pwapi){
        this.messages = messages;
        this.config =config;
        this.qsapi=qsapi;
        this.pwapi=pwapi;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length==1 && args[0].equals("hand")){
                if (player.hasPermission("shopfinder.hand")){
                    FindShopGUI gui = SearchHand(player,player.getInventory().getItemInMainHand());
                    gui.OpenPage();

                }
                else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',messages.getString("noPermission")));
                }
                //handheld search
            }
            else if (args.length==2){
                if (args[0].equals("item")){
                    if (player.hasPermission("shopfinder.item")){


                    }
                    else{
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',messages.getString("noPermission")));
                    }
                    //item search
                }
                else if(args[0].equals("name")){
                    if (player.hasPermission("shopfinder.name")){

                    }
                    else{
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',messages.getString("noPermission")));
                    }
                    //name search
                }
                else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',messages.getString("invalidArgs")));
                }
            }
            else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',messages.getString("invalidArgs")));
            }
        }
        return true;
    }
    private FindShopGUI SearchHand(Player player,ItemStack handItem){
        ArrayList<FilteredShops> filteredShops=new ArrayList<>();
        ArrayList<Warp> warpList = (ArrayList<Warp>) pwapi.getPlayerWarps(false);

        for (Shop shop : qsapi.getShopManager().getAllShops()){
            for(Warp warp :warpList) {
                ItemStack shopItem=shop.getItem();
                shopItem.setAmount(1);
                handItem.setAmount(1);
                if (shop.getItem() == handItem && shop.getLocation().distanceSquared(warp.getWarpLocation().getLocation())<= config.getInt("maxDistance")) {

                    filteredShops.add(new FilteredShops(shop,warp));
                }
            }
        }
        filteredShops.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        return new FindShopGUI(player,filteredShops,messages,config.getConfigurationSection("gui"));
    }
    private FindShopGUI SearchName(Player player,String itemName){
        ArrayList<FilteredShops> filteredShops=new ArrayList<>();
        ArrayList<Warp> warpList = (ArrayList<Warp>) pwapi.getPlayerWarps(false);

        for (Shop shop : qsapi.getShopManager().getAllShops()){
            for(Warp warp :warpList) {
                if (shop.getItem().getItemMeta().getDisplayName().contains(itemName) && shop.getLocation().distanceSquared(warp.getWarpLocation().getLocation())<= config.getInt("maxDistance")) {

                    filteredShops.add(new FilteredShops(shop,warp));
                }
            }
        }
        filteredShops.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        return new FindShopGUI(player,filteredShops,messages,config.getConfigurationSection("gui"));
    }
    private FindShopGUI SearchMaterial(Player player,String itemMaterial){
        ArrayList<FilteredShops> filteredShops=new ArrayList<>();
        ArrayList<Warp> warpList = (ArrayList<Warp>) pwapi.getPlayerWarps(false);

        for (Shop shop : qsapi.getShopManager().getAllShops()){
            for(Warp warp :warpList) {
                if (shop.getItem().getType()== Material.getMaterial(itemMaterial) && shop.getLocation().distanceSquared(warp.getWarpLocation().getLocation())<= config.getInt("maxDistance")) {

                    filteredShops.add(new FilteredShops(shop,warp));
                }
            }
        }
        filteredShops.sort((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice()));
        return new FindShopGUI(player,filteredShops,messages,config.getConfigurationSection("gui"));
    }
}
