package me.bing.wang.www.kotlintest.kotlintest.enchantment

import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.meta.ItemMeta

object CustomEnchantManager {

    val CROWN=EnchantmentWrapper("crown","Crown",1,EnchantmentTarget.ARMOR_HEAD)

    fun register(){
        var registered=Enchantment.values().contains(CROWN)
        if(!registered){
            registerEnchantment(CROWN)
        }
    }

    fun registerEnchantment(enchantment: Enchantment){
        var registered=true
        try {
            val f = Enchantment::class.java.getDeclaredField("acceptingNew")
            f.isAccessible = true
            f[null] = true
            Enchantment.registerEnchantment(enchantment)
        }catch (e:Exception){
            registered=false
            e.printStackTrace()
        }
        if(registered){
            println("[$enchantment] has been successfully registered!")
        }
    }

    fun setLore(meta: ItemMeta, lore:String){

    }
}