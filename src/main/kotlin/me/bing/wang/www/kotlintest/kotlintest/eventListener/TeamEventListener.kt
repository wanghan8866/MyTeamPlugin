package me.bing.wang.www.kotlintest.kotlintest.eventListener

import me.bing.wang.www.kotlintest.kotlintest.KotlinTestMain
import me.bing.wang.www.kotlintest.kotlintest.team.TeamManager
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.block.Banner
import org.bukkit.block.banner.Pattern
import org.bukkit.block.banner.PatternType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta
import org.bukkit.inventory.meta.ItemMeta

class TeamEventListener(val kotlinTestMain: KotlinTestMain):Listener {

    @EventHandler
    fun craftShield(e:PrepareItemCraftEvent){

        val player:Player= e.viewers[0] as Player
//        println(player)
        if(!TeamManager.inTeam(player.uniqueId)) return
        val result:ItemStack?= e.recipe?.result
        println(result)
        result?.let {
            if(it.type==Material.SHIELD){
                val meta: ItemMeta = it.itemMeta ?: return
                val bmeta:BlockStateMeta = meta as BlockStateMeta

                val banner:Banner = bmeta.blockState as Banner
                banner.baseColor= TeamManager.getTeam(player)!!.shieldColor
//                banner.addPattern(Pattern(DyeColor.WHITE,PatternType.CREEPER))

                banner.update()

                bmeta.blockState=banner
                result.itemMeta=bmeta
                e.inventory.result=result
            }
        }

    }
}