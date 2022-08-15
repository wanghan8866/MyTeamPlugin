package me.bing.wang.www.kotlintest.kotlintest.phase

import me.bing.wang.www.kotlintest.kotlintest.team.TeamManager
import org.bukkit.scheduler.BukkitRunnable

class CountDown(val phase: Phase, var coolDownTime:Int): BukkitRunnable() {
    override fun run() {
        if(coolDownTime==0){

            phase.finisher(phase)
            cancel()
        }

        TeamManager.updateAllUI()
        coolDownTime--
    }
}