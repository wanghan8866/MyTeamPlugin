package me.bing.wang.www.kotlintest.kotlintest.ui

import me.bing.wang.www.kotlintest.kotlintest.phase.Phase
import me.bing.wang.www.kotlintest.kotlintest.team.TeamType
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Team


object TeamScoreBoardManager {


    fun createScoreBoard(player: Player, phase: Phase, teamType: TeamType, teamNumberMap:MutableMap<TeamType,Int>){

        Bukkit.getScoreboardManager()?.let {
//            val scoreBoard=it.newScoreboard
            val scoreBoard=player.scoreboard
            if(scoreBoard.getObjective("new-teams-board")==null) {
                val obj=scoreBoard.registerNewObjective("new-teams-board","dummy",
                    "${ChatColor.GREEN}${ChatColor.BOLD}Team Board")
                obj.displaySlot=DisplaySlot.SIDEBAR


                val mapLine = obj.getScore("${ChatColor.AQUA}Natural Disaster Event")
                mapLine.score = 0

                val space1 = obj.getScore(" ")
                space1.score = 1

//
//
                var i=2
                for (team in TeamType.values()){
                    if(team.hidden) continue

                    val teamTeam=scoreBoard.registerNewTeam(team.displayName)
                    teamTeam.addEntry(team.chatColor.toString())
                    teamTeam.prefix="${team.chatColor}${team.textName} Players: ${ChatColor.RESET}"
                    teamTeam.suffix="${ChatColor.WHITE}${teamNumberMap[team]}"
                    teamTeam.color=team.chatColor
                    obj.getScore(team.chatColor.toString()).score=i
                    i++
                }


                val remainingLine = obj.getScore("${ChatColor.YELLOW}${ChatColor.BOLD}Remaining Player:")
                remainingLine.score = i+1

                val space2 = obj.getScore("  ")
                space2.score =  i+2


                val teamNextPhase=scoreBoard.registerNewTeam("Next phase")
                teamNextPhase.addEntry("${ChatColor.BOLD}${ChatColor.RESET}")
                teamNextPhase.prefix="${ChatColor.BOLD}${ChatColor.YELLOW}Ends in: ${ChatColor.RESET}"
                teamNextPhase.suffix="${ChatColor.WHITE}${phase.getCoolDownConverted()} seconds"
                teamNextPhase.color=teamType.chatColor
                obj.getScore("${ChatColor.BOLD}${ChatColor.RESET}").score=i+3


                val teamPhase=scoreBoard.registerNewTeam("phase")
                teamPhase.addEntry("${ChatColor.BOLD}${ChatColor.BOLD}${ChatColor.RESET}")
                teamPhase.prefix="${ChatColor.BOLD}${ChatColor.YELLOW}Name: ${ChatColor.RESET}"
                teamPhase.suffix="${ChatColor.WHITE}${phase.name.replace("_"," ")}"
                teamPhase.color=teamType.chatColor
                obj.getScore("${ChatColor.BOLD}${ChatColor.BOLD}${ChatColor.RESET}").score=i+4

                val currentPhase = obj.getScore("${ChatColor.BOLD}${ChatColor.YELLOW}Current Phase: ")
                currentPhase.score = i+5


            }

//            var obj=scoreBoard.getObjective("team-below-name")
//            if(obj==null){
//                obj=scoreBoard.registerNewObjective("team-below-name","dummy",
//                    "${teamType.chatColor}[${teamType.displayName}]")
//                obj.displaySlot=DisplaySlot.BELOW_NAME
//            }else{
//                obj.displayName="${teamType.chatColor}[${teamType.displayName}]"
//            }


            if(scoreBoard.getTeam(player.name)==null){
                val teamName:Team=scoreBoard.registerNewTeam(player.name)
                teamName.prefix="[${teamType.displayName}] ${ChatColor.RESET}"
                teamName.addEntry(player.name)
                teamName.color=teamType.chatColor
            }





            player.scoreboard=scoreBoard


        }

    }

    fun updateScoreBoard(player: Player, phase: Phase, teamType: TeamType,  teamNumberMap:MutableMap<TeamType,Int>){
        for (team in TeamType.values()){
            if(team.hidden) continue
            player.scoreboard.getTeam(team.displayName)?.suffix="${ChatColor.WHITE}${teamNumberMap[team]}"
        }

        player.scoreboard.getTeam("Next phase")?.suffix="${ChatColor.WHITE}${phase.getCoolDownConverted()} seconds"
        player.scoreboard.getTeam("phase")?.suffix="${ChatColor.WHITE}${phase.name.replace("_"," ")}"
        player.scoreboard.getTeam(player.name)?.suffix=""
    }

    fun clearScoreBoard(player: Player, isEnd:Boolean=false){

        if(isEnd){
            Bukkit.getScoreboardManager()?.let {
                val team=player.scoreboard.getTeam(player.name)
                println("clear board for ${player.name}")
                team?.unregister()

                player.scoreboard.getTeam("phase")?.unregister()
                player.scoreboard.getTeam("Next phase")?.unregister()
                for (teamTeam in player.scoreboard.teams ){

                    teamTeam.unregister()
                }
                for(obj in player.scoreboard.objectives){
                    obj.unregister()
                }

                player.scoreboard = it.newScoreboard

                player.scoreboard = it.newScoreboard
            };
        }

    }


}