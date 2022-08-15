package me.bing.wang.www.kotlintest.kotlintest.specialItem

import me.bing.wang.www.kotlintest.kotlintest.KotlinTestMain
import me.bing.wang.www.kotlintest.kotlintest.enchantment.CustomEnchantManager
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import java.util.*

object SpecialItemManager {

    val specialItemMap= mutableMapOf<String, ItemStack>()
    var main:KotlinTestMain?=null

    fun setup(main: KotlinTestMain){
        this.main=main


        val crown = ItemStack(Material.TURTLE_HELMET, 1)
        crown.addUnsafeEnchantment(CustomEnchantManager.CROWN, 1)
        crown.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
        crown.addUnsafeEnchantment(Enchantment.DURABILITY, 5)
        crown.addUnsafeEnchantment(Enchantment.MENDING, 1)
        crown.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1)
        crown.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1)


        val meta = crown.itemMeta
        if (meta != null) {
            CustomEnchantManager.setLore(meta, ChatColor.GOLD.toString() + "The Crown")
            meta.isUnbreakable = true
            val modifier = AttributeModifier(
                UUID.randomUUID(),
                "generic.armor", 6.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD
            )
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier)
            val modifier1 = AttributeModifier(
                UUID.randomUUID(),
                "generic.armorThoroughness", 4.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD
            )
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modifier1)
            val modifier2 = AttributeModifier(
                UUID.randomUUID(),
                "generic.knockBack_Resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD
            )
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, modifier2)
            meta.setDisplayName(ChatColor.GOLD.toString() + "The Crown")
            meta.setLocalizedName("The Crown")
            crown.itemMeta = meta
        }
        crown.itemMeta!!.isUnbreakable = true


        specialItemMap["crown"] = crown


        val opCrossbow = ItemStack(Material.CROSSBOW)
        val crossBowMeta=opCrossbow.itemMeta
        crossBowMeta?.setDisplayName("${ChatColor.GOLD}Machine Gun")
        crossBowMeta?.setDisplayName("Machine Gun")
        opCrossbow.itemMeta = crossBowMeta
        opCrossbow.addUnsafeEnchantment(Enchantment.QUICK_CHARGE, 5)
        opCrossbow.addUnsafeEnchantment(Enchantment.DURABILITY, 3)
        opCrossbow.addUnsafeEnchantment(Enchantment.MULTISHOT, 1)
        opCrossbow.addUnsafeEnchantment(Enchantment.PIERCING, 4)
        opCrossbow.addUnsafeEnchantment(Enchantment.MENDING, 1)
        specialItemMap["machine_gun"] = opCrossbow
    }

    fun spawnItemToPlayer(player: Player,itemName:String):Boolean{
        if(specialItemMap.containsKey(itemName)){
            player.inventory.addItem(specialItemMap[itemName])
            return true
        }
        return false
    }
}