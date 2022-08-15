package me.bing.wang.www.kotlintest.kotlintest.team

import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material

enum class TeamType(val displayName: String, val color: String, val chatColor: ChatColor, val textName:String, val symbol:Material, val shieldColor:DyeColor, val hidden:Boolean) {
    DESERT("${ChatColor.GOLD}Desert","gold",ChatColor.GOLD,"Desert",Material.SAND,DyeColor.ORANGE,false),
    JUNGLE("${ChatColor.DARK_GREEN}Jungle","dark_green",ChatColor.DARK_GREEN,"Jungle",Material.JUNGLE_LEAVES,DyeColor.GREEN,false),

    SNOW("${ChatColor.BLUE}Snow","blue",ChatColor.BLUE,"Snow",Material.SNOW_BLOCK,DyeColor.LIGHT_BLUE,false),
    PLAINS("${ChatColor.GREEN}Plains","green",ChatColor.GREEN,"Plains",Material.GRASS_BLOCK,DyeColor.LIME,false),
    ADMIN("${ChatColor.RED}Admin","red",ChatColor.RED,"Admin",Material.REDSTONE,DyeColor.RED,true);


    companion object{
        fun getTypeFromName(type:String):TeamType?{
            for (team in TeamType.values()){
                if(type==team.name){
                    return team
                }
            }
            return null
        }
    }

}