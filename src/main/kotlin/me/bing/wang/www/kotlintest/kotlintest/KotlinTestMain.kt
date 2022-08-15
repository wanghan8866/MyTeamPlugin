package me.bing.wang.www.kotlintest.kotlintest



import me.bing.wang.www.kotlintest.kotlintest.command.SimulationCommandCompleter
import me.bing.wang.www.kotlintest.kotlintest.command.SimulationCommandExecutor
import me.bing.wang.www.kotlintest.kotlintest.disaster.DisasterManager
import me.bing.wang.www.kotlintest.kotlintest.enchantment.CustomEnchantManager
import me.bing.wang.www.kotlintest.kotlintest.eventListener.GeneralListener
import me.bing.wang.www.kotlintest.kotlintest.eventListener.TeamEventListener
import me.bing.wang.www.kotlintest.kotlintest.phase.PhaseManager
import me.bing.wang.www.kotlintest.kotlintest.specialItem.SpecialItemManager
import me.bing.wang.www.kotlintest.kotlintest.team.TeamManager
import me.bing.wang.www.kotlintest.kotlintest.ui.TeamScoreBoardManager
import me.bing.wang.www.kotlintest.kotlintest.ui.UIListener
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class KotlinTestMain: JavaPlugin() {


    override fun onEnable() {
        // Plugin startup logic

        TeamManager.setUp(this)
        PhaseManager.setUp(this)
        CustomEnchantManager.register()
        SpecialItemManager.setup(this)
        DisasterManager.setup()

        val pluginManager=Bukkit.getPluginManager()
        pluginManager.registerEvents(GeneralListener(this),this)
        pluginManager.registerEvents(TeamEventListener(this),this)
        pluginManager.registerEvents(UIListener(this),this)


        getCommand("simulation")?.setExecutor(SimulationCommandExecutor(this))
        getCommand("simulation")?.tabCompleter = SimulationCommandCompleter(this)

//        val d= Sinkhole(2)





    }

    override fun onDisable() {
        // Plugin shutdown logic

        TeamManager.removeAll()
        PhaseManager.removeAll()
//        TeamScoreBoardManager.clearScoreBoard()
    }
}