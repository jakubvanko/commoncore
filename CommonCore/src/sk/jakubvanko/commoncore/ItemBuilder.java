package sk.jakubvanko.commoncore;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

/**
 * Represents an ItemStack builder
 */
public class ItemBuilder {

    protected CCMaterial ccMaterial;
    protected String name;
    protected int amount = 1;
    protected int damage = 0;
    protected boolean unbreakable = false;
    protected List<String> lore;
    protected List<ItemFlag> itemFlags;
    protected Map<Enchantment, Integer> enchantments;
    protected Map<Attribute, List<AttributeModifier>> attributeModifiers;

    /**
     * Creates a new itemstack builder with a given ccMaterial
     *
     * @param ccMaterial CCMaterial of the itemstack
     */
    public ItemBuilder(CCMaterial ccMaterial) {
        this.ccMaterial = ccMaterial;
    }

    /**
     * Sets the ccMaterial of the itemstack that will be built
     *
     * @param ccMaterial CCMaterial to set the builder to
     * @return Itself to chain commands
     */
    public ItemBuilder setMaterial(CCMaterial ccMaterial) {
        this.ccMaterial = ccMaterial;
        return this;
    }

    /**
     * Sets the amount of the itemstack that will be built
     *
     * @param amount Amount of items to set the builder to
     * @return Itself to chain commands
     */
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Sets the name of the itemstack that will be built
     *
     * @param name Name of the itemstack to set the builder to
     * @return Itself to chain commands
     */
    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the lore of the itemstack that will be built
     *
     * @param lore Lore to set the builder to
     * @return Itself to chain commands
     */
    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Sets the itemflags of the itemstack that will be built
     *
     * @param itemFlags Itemflags to set the builder to
     * @return Itself to chain commands
     */
    public ItemBuilder setItemFlags(List<ItemFlag> itemFlags) {
        this.itemFlags = itemFlags;
        return this;
    }

    /**
     * Sets the enchantments of the itemstack that will be built to the given levels
     *
     * @param enchantments Enchantments with their levels to set the builder to
     * @return Itself to chain commands
     */
    public ItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    /**
     * Sets the unbreakability of the itemstack that will be built
     *
     * @param unbreakable Unbreakability to set the builder to
     * @return Itself to chain commands
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    /**
     * Sets the attribute modifiers of the itemstack that will be built
     *
     * @param attributeModifiers Attribute modifiers to set the builder to
     * @return Itself to chain commands
     */
    public ItemBuilder setAttributeModifiers(Map<Attribute, List<AttributeModifier>> attributeModifiers) {
        this.attributeModifiers = attributeModifiers;
        return this;
    }

    /**
     * Sets the damage of the itemstack that will be built
     *
     * @param damage Damage to set the builder to
     * @return Itself to chain commands
     */
    public ItemBuilder setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Build the itemstack according to previously specified parameters
     *
     * @return Itemstack with given parameters
     */
    public ItemStack build() {
        ItemStack itemStack = ccMaterial.createItem(amount);
        if (enchantments != null) {
            itemStack.addUnsafeEnchantments(enchantments);
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (name != null) {
            itemMeta.setDisplayName(name);
        }
        if (lore != null) {
            itemMeta.setLore(lore);
        }
        if (itemFlags != null) {
            for (ItemFlag itemFlag : itemFlags) {
                itemMeta.addItemFlags(itemFlag);
            }
        }
        itemStack.setItemMeta(itemMeta);
        // This assures compatibility with older versions
        if (CCMaterial.isNewVersion()) {
            itemMeta.setUnbreakable(unbreakable);
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(damage);
            if (attributeModifiers != null) {
                for (Map.Entry<Attribute, List<AttributeModifier>> entry : attributeModifiers.entrySet()) {
                    for (AttributeModifier attributeModifier : entry.getValue()) {
                        itemMeta.addAttributeModifier(entry.getKey(), attributeModifier);
                    }
                }
            }
            itemStack.setItemMeta((ItemMeta) damageable);
        }
        return itemStack;
    }
}