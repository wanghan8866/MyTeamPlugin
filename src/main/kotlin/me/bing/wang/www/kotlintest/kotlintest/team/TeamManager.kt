package me.bing.wang.www.kotlintest.kotlintest.team

import me.bing.wang.www.kotlintest.kotlintest.KotlinTestMain
import me.bing.wang.www.kotlintest.kotlintest.phase.Phase
import me.bing.wang.www.kotlintest.kotlintest.phase.PhaseManager
import me.bing.wang.www.kotlintest.kotlintest.ui.TeamScoreBoardManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*

object TeamManager {
    var kotlinTestMain: KotlinTestMain?=null
    val teams:MutableMap<UUID,TeamType> = mutableMapOf()
    const val maxPeople=25


    fun setUp(kotlinTestMain: KotlinTestMain){
        this.kotlinTestMain=kotlinTestMain
//        for(player in Bukkit.getOnlinePlayers()){
//            joinTeam(player.uniqueId,TeamType.SURVIVOR)
//        }
    }

    fun joinTeam(uuid: UUID,teamType: TeamType){

//        removeTeam(uuid)

        if(inTeam(uuid)){
            Bukkit.getPlayer(uuid)?.sendMessage("${ChatColor.RED}+You are already in a team: ${teams[uuid]}!!")
            return
        }
        teams[uuid] = teamType
        Bukkit.getPlayer(uuid)?.let {


            Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "team join ${teamType.name} ${it.name}"
            )
            setStartColor(it,teamType)
            it.sendMessage("${ChatColor.GREEN}You join the team: [${teams[uuid]}] successfully.")
            it.sendTitle("","",0,10,0)
        }



        updateAllUI()
    }

    fun setStartColor(player: Player, teamType: TeamType){
        kotlinTestMain?.let {
            main->
            TeamScoreBoardManager.createScoreBoard(player, Phase.dummyPhase(main),teamType, getTeamCounterMap())
            player.setDisplayName("${teamType.chatColor} [${teamType.displayName}] ${player.name} ${ChatColor.RESET}")
            player.setPlayerListName(player.displayName)


        }

    }

    fun removeTeam(uuid: UUID){

        if(!inTeam(uuid)){
            Bukkit.getPlayer(uuid)?.sendMessage("${ChatColor.RED}+You are not in any team!!")
            return
        }
        println("${Bukkit.getOfflinePlayer(uuid).name} may be removed!")
        Bukkit.getPlayer(uuid)?.let {
            Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "team leave ${ it.name}"
            )

            it.sendMessage("${ChatColor.GREEN}You are removed from team: [${teams[uuid]}] successfully.")
//            it.removePotionEffect(PotionEffectType.HUNGER)
            it.setDisplayName("${it.name}")
            it.setPlayerListName(it.displayName)
            TeamScoreBoardManager.clearScoreBoard(it)

        }


        teams.remove(uuid)

        updateAllUI()
    }

    fun updateAllUI(){

        val teamNumberMap= getTeamCounterMap()
        for(uuid in teams.keys){
            val type:TeamType= teams.getValue(uuid)
            Bukkit.getPlayer(uuid)?.let {

                PhaseManager.currentPhase?.let {
                    phase->    TeamScoreBoardManager.updateScoreBoard(it,phase,type, teamNumberMap)
                } ?: TeamScoreBoardManager.updateScoreBoard(it, Phase.dummyPhase(kotlinTestMain!!),type, teamNumberMap)


            }

        }
    }
    fun getTeamCounterMap(): MutableMap<TeamType,Int>{
        val teamNumberMap= mutableMapOf<TeamType, Int>()
        TeamType.values().forEach {
            teamNumberMap.put(it, teamCounter(it))
        }
        return teamNumberMap
    }

    fun inTeam(uuid: UUID):Boolean{
        return teams.containsKey(uuid)
    }

    fun removeAll(){
        for (uuid in teams.keys){
            removeTeam(uuid)
        }
        teams.clear()

        for (type in TeamType.values()) {
            Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "team remove " + type.name)
        }
        clearUI()
    }

    fun teamCounter(teamType: TeamType, isOnline:Boolean=true):Int{
        var size=0
        for (uuid in teams.keys){
            if(teams[uuid]==teamType){
                if(isOnline){
                    Bukkit.getPlayer(uuid)?.let {
                        size++
                    }
                }else{
                    size++
                }


            }
        }

        return size
    }


    fun getTeam(player: Player):TeamType?{
        return teams.getOrDefault(player.uniqueId,null)
    }

    fun sendMessageToAll(msg:String){
        for (player in Bukkit.getOnlinePlayers()){
            player.sendMessage(msg)
        }
    }
    fun sendTitleToAll(msg:String,sub:String){
        for (player in Bukkit.getOnlinePlayers()){
            player.sendTitle(msg,sub,20,70,20)
        }
    }

    fun sendMessageToTeam(msg:String,teamType: TeamType){
        for (uuid in teams.keys){
            Bukkit.getPlayer(uuid)?.let{
                if(teams.getValue(uuid)==teamType){
                    it.sendMessage(msg)
                }
            }
        }
    }

    fun sendTitleToTeam(msg:String,sub:String,teamType: TeamType){
        for (uuid in teams.keys){
            Bukkit.getPlayer(uuid)?.let{
                if(teams.getValue(uuid)==teamType){
                    it.sendTitle(msg,sub,20,70,20)
                }
            }
        }
    }

    fun giveEffectToAll(effect: PotionEffect){
        for (player in Bukkit.getOnlinePlayers()){
            player.addPotionEffect(effect)
        }
    }

    fun removeEffects(potionEffect: PotionEffectType){
        for (player in Bukkit.getOnlinePlayers()){
            player.removePotionEffect(potionEffect)
        }
    }

    fun printAll() {
        for (set in teams){
            println("uuid: ${set.component1()} -> ${set.component2()}")
        }
    }

    fun clearUI(){
        for (uuid in teams.keys){
            Bukkit.getPlayer(uuid)?.let {
                TeamScoreBoardManager.clearScoreBoard(it,true)
            }

        }
    }


}