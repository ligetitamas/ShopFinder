package me.ltommi.shopfinder.utils;

import com.olziedev.playerwarps.api.warp.Warp;
import org.bukkit.inventory.ItemStack;
import org.maxgamer.quickshop.api.shop.Shop;

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
