package me.ltommi.shopfinder.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class FindShopGUI {
    private FileConfiguration messages;
    private ConfigurationSection guiConfig;
    private Player player;
    private ArrayList<FilteredShops> shops;
    private String title;
    private int maxPage;
    private int currentPage;
    private int nextItemSlot;
    private ItemStack prevPage;
    private ItemStack nextPage;
    private Inventory inv;
    public FindShopGUI(Player player, ArrayList<FilteredShops> shops, FileConfiguration messages, ConfigurationSection guiConfig){
        this.messages=messages;
        this.guiConfig=guiConfig;
        this.player=player;
        this.shops=shops;
        this.title=messages.getString("guiName");
        NavSetup();
        currentPage =1;
        nextItemSlot= 11;
        maxPage=(int) Math.ceil(shops.size()/15);
        inv=Bukkit.createInventory(null,45,title);
        //player.sendMessage("létrejött a gui");
       // player.sendMessage(shops.size()+"");
    }
    public void OpenPage(){
        inv.clear();
        int pageItemAmount=15;
        if (maxPage==currentPage){
            pageItemAmount= shops.size()%(15);
        }
        for (int i=0; i<pageItemAmount; i++){
            FilteredShops shop = shops.get((currentPage-1)*15+i);
            ItemStack displayItem=shop.getItem();
            ItemMeta dpitemMeta =displayItem.getItemMeta();
            List<String> lore =guiConfig.getStringList("itemLore");
            for (String row : lore){
                row.replace("%price%",(shop.getPrice()+""));
            }
            dpitemMeta.setLore(lore);
            AddShop(displayItem);
        }
        //player.sendMessage("nyilik");
        player.openInventory(inv);
    }
    private void AddShop(ItemStack item){
        inv.setItem(nextItemSlot,item);
        if ((nextItemSlot++)%9==7){
            nextItemSlot+=5;
        }
        else{
            nextItemSlot++;
        }
    }
    private void NavSetup(){
        prevPage= new ItemStack(Material.matchMaterial(guiConfig.getString("prevPage.material")));
        ItemMeta prevPageMeta = prevPage.getItemMeta();
        try{
            prevPageMeta.setCustomModelData(guiConfig.getInt("prevPage.customModelData"));
        } catch (Exception e){ //no cmm set, not necessary
        }
        prevPageMeta.setDisplayName(messages.getString("prevPage"));
        prevPage.setItemMeta(prevPageMeta);

        nextPage= new ItemStack(Material.matchMaterial(guiConfig.getString("nextPage.material")));
        ItemMeta nextPageMeta = nextPage.getItemMeta();
        try{
            nextPageMeta.setCustomModelData(guiConfig.getInt("nextPage.customModelData"));
        } catch (Exception e){ //no cmm set, not necessary
        }
        nextPageMeta.setDisplayName(messages.getString("nextPage"));
        nextPage.setItemMeta(nextPageMeta);
    }


}
