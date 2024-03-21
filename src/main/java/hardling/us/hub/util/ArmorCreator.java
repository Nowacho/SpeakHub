package hardling.us.hub.util;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.List;

public class ArmorCreator extends ItemStack {
    private ItemStack itemStack;

    public ArmorCreator(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ArmorCreator(Material material) {
        this(material, 1);
    }

    public ArmorCreator(Material material, int amount) {
        this(material, amount, (short) 0);
    }

    public ArmorCreator(Material material, int amount, short damage) {
        this(new ItemStack(material, amount, damage));
    }


    public ArmorCreator setColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);
        this.itemStack.setItemMeta(leatherArmorMeta);
        return this;
    }

    public ArmorCreator setDurability(int durability) {
        this.itemStack.setDurability((short) durability);
        return this;
    }

    public ArmorCreator setData(int data) {
        this.itemStack.setData(new MaterialData(data));
        return this;
    }
    public ArmorCreator setDisplayName(String name) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ArmorCreator addLore(String lore) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        List<String> lores = itemMeta.getLore();
        if (lores == null)
            lores = Lists.newArrayList();
        lores.add(CC.translate(lore));
        itemMeta.setLore(lores);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ArmorCreator setLore(String... lores) {
        clearLore();
        for (String lore : lores)
            addLore(lore);
        return this;
    }

    public ArmorCreator setLore(List<String> lore) {
        clearLore();
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(CC.translate(lore));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ArmorCreator clearLore() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(Lists.newArrayList());
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ArmorCreator setGlow(boolean type) {
        if (type) {
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemStack build() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        ItemStack clone = this.itemStack.clone();
        clone.setItemMeta(itemMeta.clone());
        return clone;
    }


    public static Inventory createInventory(String title, int rows) {
        Inventory inv = Bukkit.getServer().createInventory(null, rows * 9, CC.translate(title));
        return inv;
    }

    public static ItemStack createItemStack(String name, List<String> lore, Material material, int amount, int data) {
        ItemStack item = new ItemStack(material, amount, (short) data);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(CC.translate(name));
        itemmeta.setLore(CC.translateFromArray(lore));
        item.setItemMeta(itemmeta);
        return item;
    }

    public ArmorCreator color(Color color) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (!(meta instanceof LeatherArmorMeta))
            throw new UnsupportedOperationException("Cannot set color of FileUtil non-leather armor item.");
        ((LeatherArmorMeta) meta).setColor(color);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public static ItemStack createItemStackWithoutLore(String name, Material material, int amount, int data) {
        ItemStack item = new ItemStack(material, amount, (short) data);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(CC.translate(name));
        item.setItemMeta(itemmeta);
        return item;
    }

    public ArmorCreator setSkullOwner(String owner) {
        SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();
        skullMeta.setOwner(owner);
        this.itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ArmorCreator setName(String displayName) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        return this;
    }

    public ItemStack create() {
        return this.itemStack;
    }
}
