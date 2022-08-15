package me.bing.wang.www.kotlintest.kotlintest.eventListener

import me.bing.wang.www.kotlintest.kotlintest.KotlinTestMain
import me.bing.wang.www.kotlintest.kotlintest.phase.PhaseManager
import me.bing.wang.www.kotlintest.kotlintest.team.TeamManager
import me.bing.wang.www.kotlintest.kotlintest.team.TeamType
import me.bing.wang.www.kotlintest.kotlintest.ui.TeamScoreBoardManager
import org.bukkit.BanList
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.server.ServerLoadEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class GeneralListener(val kotlinTestMain: KotlinTestMain):Listener {
    @EventHandler
    fun onJoin(e:PlayerJoinEvent){
////        e.player.sendMessage("hello from Kotlin!")
////        TeamManager.joinTeam(e.player.uniqueId,TeamType.SURVIVOR)
//        TeamScoreBoardManager.clearScoreBoard(e.player)
        TeamManager.getTeam(e.player)?.let {

//            TeamManager.removeTeam(e.player.uniqueId)
//            TeamManager.joinTeam(e.player.uniqueId,it)
            TeamManager.setStartColor(e.player,it)


        }?:run {
            e.player.sendTitle("Please, join your team","using /simulation team join",10,60*60*20,10)
        }

        if(PhaseManager.currentPhase?.name?.contains("waiting") == true){
            e.player.inventory.clear()
            e.player.inventory.addItem(ItemStack(Material.COOKED_BEEF,64))
        }
    }

    @EventHandler
    fun onDrop(e:PlayerDropItemEvent){
        if(PhaseManager.currentPhase?.name?.contains("waiting") == true){
            val player=e.player
            if(TeamManager.getTeam(player)!=TeamType.ADMIN){
                e.isCancelled=true
            }
        }
    }

    @EventHandler
    fun onServerLoad(e:ServerLoadEvent){
        for (type in TeamType.values()) {
            Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "team list")
            Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "team remove " + type.name)
            Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "team add " + type.name)
            Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "team modify " + type.name + " color " + type.color
            )
            Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "team modify " + type.name + " prefix " + "{\"text\":\"[" + type.name + "] \",\"color\":\"" + type.color + "\"}"
            )
        }
    }

    @EventHandler
    fun onPlayerDied(e:PlayerDeathEvent){
        if(TeamManager.inTeam(e.entity.uniqueId)){
            TeamManager.removeTeam(e.entity.uniqueId)
            e.entity.kickPlayer("You just died in the event, but thank you for participating!")
            Bukkit.getBanList(BanList.Type.NAME).addBan(e.entity.name,"${ChatColor.RED}You just died in the event, but thank you for participating!",
                Date(Date().time+(1000*60*60*24)),""
            )
        }
    }
}