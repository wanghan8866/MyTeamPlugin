package me.bing.wang.www.kotlintest.kotlintest.enchantment

import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack

class EnchantmentWrapper(
    private val nameSpace:String,
    private val name:String,
    private val maxLevel:Int,
    private val enchantmentTarget: EnchantmentTarget
): Enchantment(NamespacedKey.minecraft(nameSpace)) {
    override fun getName(): String {
        return name
    }

    override fun getMaxLevel(): Int {
        return maxLevel
    }

    override fun getStartLevel(): Int {
        return 0
    }

    override fun getItemTarget(): EnchantmentTarget {
        return enchantmentTarget
    }

    override fun isTreasure(): Boolean {
        return false
    }

    override fun isCursed(): Boolean {
        return false
    }

    override fun conflictsWith(other: Enchantment): Boolean {
        return false
    }

    override fun canEnchantItem(item: ItemStack): Boolean {
        return false
    }


}