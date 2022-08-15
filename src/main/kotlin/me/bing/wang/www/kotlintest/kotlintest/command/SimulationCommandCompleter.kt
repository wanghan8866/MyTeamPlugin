package me.bing.wang.www.kotlintest.kotlintest.command

import me.bing.wang.www.kotlintest.kotlintest.KotlinTestMain
import me.bing.wang.www.kotlintest.kotlintest.phase.PhaseManager
import me.bing.wang.www.kotlintest.kotlintest.specialItem.SpecialItemManager
import me.bing.wang.www.kotlintest.kotlintest.team.TeamManager
import me.bing.wang.www.kotlintest.kotlintest.team.TeamType
import me.bing.wang.www.kotlintest.kotlintest.ui.TeamUI
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class SimulationCommandCompleter(val kotlinTestMain: KotlinTestMain):TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        val result= mutableListOf<String>()

        if(sender is Player){
            if(!sender.isOp){
                if(args.size==1){
                    result.add("team")
                }
                else if(args.size==2&&args[0]=="team"){
                    result.add("join")
                }
                return result
            }
            when{
                args.size==1->{
                    result.add("pvp")
                    result.add("team")
                    result.add("phase")
                    result.add("give")
                }
                args.size==2&&args[0]=="pvp"->{
                    result.add("on")
                    result.add("off")
                }
                args.size==2&&args[0]=="team"->{
                    result.add("join")
                    result.add("remove")
                }
                args.size==3&&args[0]=="team"&&args[1]=="join"->{
                    for(team in TeamType.values()){
                        result.add(team.name)
                    }
                }
                args.size==4&&args[0]=="team"&&args[1]=="join"&&args[2] in TeamType.values().map { it.name }->{
                    for(player in Bukkit.getOnlinePlayers()){
                        result.add(player.name)
                    }
                }
                args.size==3&&args[0]=="team"&&args[1]=="remove"->{
                    for(id in TeamManager.teams.keys){
                        Bukkit.getPlayer(id)?.let {
                           result.add(it.name)
                        }

                    }
                }
                args.size==2&&args[0]=="phase"->{
                    result.add("create")
                    result.add("start")
                    result.add("end")
                    result.add("modify")
                }
                args.size==3&&args[0]=="phase"&&args[1]=="create"->{
                    result.add("<phase_name>")
                }
                args.size==4&&args[0]=="phase"&&args[1]=="create"->{
                    result.add("<phase_time:seconds>")
                }
                args.size==3&&args[0]=="phase"&&args[1]=="start"->{
                    for(phase in PhaseManager.phases){
                        result.add(phase.name)
                    }
                }
                args.size==3&&args[0]=="phase"&&args[1]=="modify"->{
                    for(phase in PhaseManager.phases){
                        result.add(phase.name)
                    }
                }
                args.size==4&&args[0]=="phase"&&args[1]=="modify"&&args[2] in PhaseManager.phases.map {it.name}->{
                    result.add("<new_phase_time:seconds>")
                }
                args.size==2&&args[0]=="give"->{
                    for (name:String in SpecialItemManager.specialItemMap.keys){
                        result.add(name)
                    }
                }
                args.size==3&&args[0]=="give"&&args[1] in SpecialItemManager.specialItemMap.keys->{
                    for (player in Bukkit.getOnlinePlayers()){
                        result.add(player.name)
                    }
                }

            }

        }

        return result
    }
}