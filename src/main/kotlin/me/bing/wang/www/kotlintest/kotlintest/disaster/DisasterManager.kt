package me.bing.wang.www.kotlintest.kotlintest.disaster

import deadlydisasters.disasters.Disaster
import deadlydisasters.general.DifficultyLevel
import org.bukkit.Bukkit

object DisasterManager {

    fun setup(){

        Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters difficulty ALL_WORLDS ${DifficultyLevel.CUSTOM}")
        Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters disable randomdisasters ALL_WORLDS")
        Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters mintimer ALL_WORLDS 180")
    }

    fun start(){

        Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters enable randomdisasters ALL_WORLDS")
        Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters mintimer ALL_WORLDS 30")
        Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters difficulty ALL_WORLDS ${DifficultyLevel.CUSTOM}")
        for (disaster in Disaster.values()){
            Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters disable ${disaster.name} ALL_WORLDS")
        }
        Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters enable ${Disaster.EXTREMEWINDS} ALL_WORLDS")
        Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters enable ${Disaster.TORNADO} ALL_WORLDS")
        Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters enable ${Disaster.TSUNAMI} ALL_WORLDS")
        Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters enable ${Disaster.HURRICANE} ALL_WORLDS")
//        Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters enable ${Disaster.GEYSER} ALL_WORLDS")


    }

    fun end(){
        Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "disasters disable randomdisasters ALL_WORLDS")
    }
}