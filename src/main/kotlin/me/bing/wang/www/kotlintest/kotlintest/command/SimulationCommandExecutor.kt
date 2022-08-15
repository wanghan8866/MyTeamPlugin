package me.bing.wang.www.kotlintest.kotlintest.command

import me.bing.wang.www.kotlintest.kotlintest.KotlinTestMain
import me.bing.wang.www.kotlintest.kotlintest.phase.PhaseManager
import me.bing.wang.www.kotlintest.kotlintest.specialItem.SpecialItemManager
import me.bing.wang.www.kotlintest.kotlintest.team.TeamManager
import me.bing.wang.www.kotlintest.kotlintest.team.TeamType
import me.bing.wang.www.kotlintest.kotlintest.ui.TeamUI
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

class SimulationCommandExecutor(val kotlinTestMain: KotlinTestMain):CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {


        if(sender is Player){
            if(!sender.isOp){

                if(args.size==1&&args[0]=="team"){
                    if(TeamManager.inTeam(sender.uniqueId)){
                        sender.sendMessage("You in team: [${TeamManager.getTeam(sender)?.displayName}]")
                    }else{
                        sender.sendMessage("${ChatColor.RED}You are not in any team yet!")
                    }

                }
                else if(args.size==2&&args[0]=="team"&&args[1]=="join"){
                    TeamUI(sender)
                }
                return false
            }
            when{
                args.size==1&&args[0]=="team"->{
                    if(TeamManager.inTeam(sender.uniqueId)){
                        val teamType:TeamType=TeamManager.getTeam(sender)!!
                        sender.sendMessage("You in team: ${teamType.chatColor}[${teamType.textName}]")
                    }else{
                        sender.sendMessage("${ChatColor.RED}You are not in any team yet!")
                    }
                }

                args.size==2&&args[0]=="pvp"->{
                    println(args[1])
                    if(args[1]== "on"){
                        sender.world.pvp=true
                    }else if(args[1]== "off"){
                        sender.world.pvp=false
                    }
                }
                args.size==2&&args[0]=="team"&&args[1]=="join"->{
                    TeamUI(sender)
                }

                args.size==3&&args[0]=="team"&&args[1]=="join"->{
                    TeamType.getTypeFromName(args[2])?.let { TeamManager.joinTeam(sender.uniqueId, it) }?:sender.sendMessage("${ChatColor.RED}Invalid team name!")
                }
                args.size==4&&args[0]=="team"&&args[1]=="join"&&args[2] in TeamType.values().map { it.name }->{
                    val player=Bukkit.getPlayer(args[3])
                    TeamType.getTypeFromName(args[2])?.let {
                        player?.let { player -> TeamManager.joinTeam(player.uniqueId, it)
                        } ?:sender.sendMessage("${ChatColor.RED}Invalid player name!")


                    } ?:sender.sendMessage("${ChatColor.RED}Invalid team name!")
                }
                args.size==2&&args[0]=="team"&&args[1]=="remove"->{
                   TeamManager.removeTeam(sender.uniqueId)
                }
                args.size==3&&args[0]=="team"&&args[1]=="remove"->{
                    Bukkit.getPlayer(args[2])?.let { TeamManager.removeTeam(it.uniqueId) }
                }

                args.size==4&&args[0]=="phase"&&args[1]=="create"->{
                    try {
                        val time=args[3].toInt()
                        val name=args[2]
                        if(time<1) throw NumberFormatException("The time of a phase can not be negative!")

                        if(PhaseManager.containPhaseByName(name)){
                            sender.sendMessage("${ChatColor.RED}Phase name is used!")
                            return false
                        }
                        if(PhaseManager.createPhase(name,time)){
                            sender.sendMessage("${ChatColor.GREEN} You create a phase successfully!")
                        }else{
                            sender.sendMessage("${ChatColor.RED} You create a phase unsuccessfully!")
                        }

                    }
                    catch (e:NumberFormatException){
                        sender.sendMessage("${ChatColor.RED}Invalid time: ${e.message}")
                    }

                }
                args.size==2&&args[0]=="phase"&&args[1]=="end"->{
                    PhaseManager.currentPhase?.let {
                        if(PhaseManager.endPhase()){
                            sender.sendMessage("${ChatColor.GREEN} You end a phase successfully!")
                        }else{
                            sender.sendMessage("${ChatColor.RED} You end a phase unsuccessfully!")
                        }

                    }?:   sender.sendMessage("${ChatColor.RED}Not in any phase now!")

                }
                args.size==3&&args[0]=="phase"&&args[1]=="start"->{

                    if(!PhaseManager.containPhaseByName(args[2])){
                        sender.sendMessage("${ChatColor.RED} invalid phase name!")
                        return false
                    }
                    PhaseManager.currentPhase?.let {
                        sender.sendMessage("${ChatColor.RED}Already in any phase now!")
                    }?: run {
                        if(PhaseManager.startPhase(args[2])){
                            sender.sendMessage("${ChatColor.GREEN} You start a phase successfully!")
                        }else{
                            sender.sendMessage("${ChatColor.RED} You start a phase unsuccessfully!")
                        }

                    }

                }

                args.size==4&&args[0]=="phase"&&args[1]=="modify"&&args[2] in PhaseManager.phases.map {it.name}->{
                    try {
                        val time=args[3].toInt()
                        val name=args[2]
                        if(time<1) throw NumberFormatException("The time of a phase can not be negative!")
                        if(!PhaseManager.containPhaseByName(name)){
                            sender.sendMessage("${ChatColor.RED} invalid phase name!")
                            return false
                        }
                        if(PhaseManager.modifyPhase(name,time)){
                            sender.sendMessage("${ChatColor.GREEN} You modify a phase successfully!")
                        }else{
                            sender.sendMessage("${ChatColor.RED} You modify a phase unsuccessfully!")
                        }

                    }
                    catch (e:NumberFormatException){
                        sender.sendMessage("${ChatColor.RED}Invalid time: ${e.message}")
                    }
                }
                args.size==2&&args[0]=="give"->{
                    val itemName=args[1]

                    if(SpecialItemManager.spawnItemToPlayer(sender,itemName)){
                        sender.sendMessage("${ChatColor.GREEN}Successfully give item [${itemName}] to you!")
                    }else{
                        sender.sendMessage("${ChatColor.RED}Fail to give item [${itemName}]!")
                    }
                }
                args.size==3&&args[0]=="give"->{
                    val itemName=args[1]
                    val player:Player?=Bukkit.getPlayer(args[2])
                    player?.let {
                        if(SpecialItemManager.spawnItemToPlayer(it,itemName)){
                            sender.sendMessage("${ChatColor.GREEN}Successfully give item [${itemName}] to ${it.name}!")
                        }else{
                            sender.sendMessage("${ChatColor.RED}Fail to give item [${itemName}]!")
                        }
                    }?:run {
                        sender.sendMessage("${ChatColor.RED}Fail to give item [${itemName}] as Player ${args[2]} does not exist!")
                    }

                }
                else->{
                    sender.sendMessage("${ChatColor.RED}Invalid commands!")
                }

            }
        }else if(sender is ConsoleCommandSender){
            if(args.size==1&&args[0]=="list"){

                TeamManager.printAll()
            }
        }

        return false
    }
}