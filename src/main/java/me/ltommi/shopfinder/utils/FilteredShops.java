package me.ltommi.shopfinder.utils;

import com.ghostchu.quickshop.api.shop.Shop;
import com.olziedev.playerwarps.api.warp.Warp;
import org.bukkit.inventory.ItemStack;


public class FilteredShops {
    public FilteredShops(Shop shop, Warp warp){
        this.warp=warp;
        this.shop=shop;
    }
    private Warp warp;
    private Shop shop;
    public ItemStack getItem(){
        return shop.getItem();
    }
    public double getPrice(){
        return shop.getPrice();
    }
}
