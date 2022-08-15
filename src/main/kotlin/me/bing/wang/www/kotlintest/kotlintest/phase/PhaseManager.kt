package me.bing.wang.www.kotlintest.kotlintest.phase

import me.bing.wang.www.kotlintest.kotlintest.KotlinTestMain
import me.bing.wang.www.kotlintest.kotlintest.disaster.DisasterManager
import me.bing.wang.www.kotlintest.kotlintest.team.TeamManager
import org.bukkit.Bukkit
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object PhaseManager {
    var kotlinTestMain:KotlinTestMain?=null
    val phases:MutableList<Phase> = mutableListOf()
    var currentPhase:Phase?=null

    fun setUp(kotlinTestMain: KotlinTestMain){
        this.kotlinTestMain=kotlinTestMain

        val phase3:Phase= Phase(kotlinTestMain,"Phase_3",60*60,
        "Heavy natural disasters: Meteor showers, Earthquakes, E.T.C. Phase ends with multiple supernovas that the teams have to work together to survive.",
            {
                TeamManager.sendTitleToAll("${it.name.replace("_"," ")} started","With heavy natural disasters ")
                TeamManager.sendMessageToAll(it.description)

            },
            {
                TeamManager.sendTitleToAll("${it.name.replace("_"," ")} ended","")
                Bukkit.getScheduler().runTaskLater(
                    kotlinTestMain, Runnable {
                                             TeamManager.sendTitleToAll("The event has ended!","Thank you for playing!")
                    },20+70+20
                )
                currentPhase =null
            })
        val phase2:Phase= Phase(kotlinTestMain,"Phase_2",60*60,
            "Borders down, PVP ON. Light natural disasters: Strong winds, sinkholes, E.T.C. ",
            {
                Bukkit.getWorld("world")?.pvp=true
                TeamManager.sendTitleToAll("${it.name.replace("_"," ")} started","PvP turned On with mild natural disasters")
                TeamManager.sendMessageToAll(it.description)
            },
            {

                phase3.start()
//                DisasterManager.end()
//                Bukkit.getScheduler().runTaskLater(
//                    kotlinTestMain,
//                    Runnable {
//                        TeamManager.sendTitleToAll("${it.name.replace("_"," ")} ended","")
//
//                    },
//                    20+70+20
//                )


            })


        val phase1:Phase= Phase(kotlinTestMain,"Phase_1",60*60,
            "Exploration, Gathering materials, Picking leaders. (Borders up, PvP OFF). Lasts for 1 hour.",
            {
                Bukkit.getWorld("world")?.pvp=false
                TeamManager.sendTitleToAll("${it.name.replace("_"," ")} started","PvP turned OFF")
//                TeamManager.giveEffectToAll(PotionEffect(PotionEffectType.HUNGER,20*60*30,0))
                TeamManager.sendMessageToAll(it.description)
//                DisasterManager.start()

            },
            {
                Bukkit.getWorld("world")?.pvp=true
                phase2.start()


            })
        val waitingPhase:Phase=Phase(kotlinTestMain,"waiting",60*60,
            "",
            {
                Bukkit.getWorld("world")?.pvp=false
                TeamManager.sendTitleToAll("${it.name.replace("_"," ")} started","PvP OFF for one hour.")
                Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "give @a minecraft:cooked_beef 64")
//                TeamManager.sendMessageToAll(it.description)
            },
            {
                Bukkit.getWorld("world")?.pvp=false
//                TeamManager.sendTitleToAll("${it.name.replace("_"," ")} ended","")
                Bukkit.dispatchCommand(Bukkit.getServer().consoleSender, "clear @a")
                phase1.start()

            })

        phases.add(
           waitingPhase
        )
        phases.add(phase1)
        phases.add(phase2)
        phases.add(phase3)
    }

    fun createPhase(name: String,time: Int):Boolean{
        kotlinTestMain?.let {
            main->Phase.dummyPhase(main,name,time)
        }

        return true

    }

    fun startPhase(name: String):Boolean{
//        println(phases)

        if(currentPhase!=null) return false
        currentPhase= getPhaseByName(name)
        if(currentPhase==null) return false
        currentPhase!!.start()
        return true
    }

    fun endPhase():Boolean{
        if(currentPhase==null) return false
        if(currentPhase!!.countDown==null) return false
        currentPhase!!.cancel()
        currentPhase=null
        return true

    }

    fun modifyPhase(name:String, time:Int):Boolean{
        if (!containPhaseByName(name)) return false
        getPhaseByName(name)?.let {
            it.countDown?.coolDownTime=time

        }?:return false
        return true
    }

    fun containPhaseByName(name: String):Boolean{
        for (phase in phases){
            if(phase.name==name) return true
        }
        return false
    }

    fun getPhaseByName(name: String):Phase?{
        for (phase in phases){
            if(phase.name==name) return phase
        }
        return null
    }

    fun removeAll(){

        currentPhase?.cancel()
        phases.clear()
    }
}