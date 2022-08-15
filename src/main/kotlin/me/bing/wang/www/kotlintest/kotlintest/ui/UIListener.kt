package me.bing.wang.www.kotlintest.kotlintest.ui

import me.bing.wang.www.kotlintest.kotlintest.KotlinTestMain
import me.bing.wang.www.kotlintest.kotlintest.team.TeamManager
import me.bing.wang.www.kotlintest.kotlintest.team.TeamType
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class UIListener(val kotlinTestMain: KotlinTestMain):Listener {

    @EventHandler
    fun onInventoryClick(e:InventoryClickEvent){
        if(e.currentItem==null) return
        if(e.currentItem!!.itemMeta==null) return
        val currentItem:ItemStack = e.currentItem!!
        val meta:ItemMeta=currentItem.itemMeta!!
        val player:Player=e.whoClicked as Player

        when{
            e.view.title.contains("Team Selection")->{
                val teamType:TeamType=TeamType.valueOf(meta.localizedName)
                player.closeInventory()
                e.isCancelled=true
                if(TeamManager.getTeam(player)!=null){
                    player.sendMessage("${ChatColor.RED}You have already chosen a team!")
                }else{
                    if(TeamManager.teamCounter(teamType,false)>TeamManager.maxPeople){
                        player.sendMessage(ChatColor.RED.toString() + "Too many people in this team, please choose another team!")
                    }else{
                        ConfirmationUI(player,teamType.name)
                    }
                }
            }
            e.view.title.contains("Confirmation For Team")->{
                val result:String =meta.displayName
                player.closeInventory()
                e.isCancelled=true

                if(result.contains("yes",true)){
                    val teamType=TeamType.valueOf(meta.localizedName)
                    TeamManager.joinTeam(player.uniqueId,teamType)
                    player.sendMessage(ChatColor.GREEN.toString() + "You are now on " + teamType.displayName + ChatColor.GREEN + " team!")
                }else if(result.contains("cancel",true)){
                    TeamUI(player)
                }
            }
        }
    }
}