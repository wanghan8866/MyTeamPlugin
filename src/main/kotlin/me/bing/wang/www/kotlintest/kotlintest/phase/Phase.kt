package me.bing.wang.www.kotlintest.kotlintest.phase


import me.bing.wang.www.kotlintest.kotlintest.KotlinTestMain

class Phase(val main: KotlinTestMain, val name:String, var time:Int,val description:String, val starter: (Phase) -> Unit,  val finisher:(Phase) -> Unit) {

    var countDown:CountDown? = CountDown(this,time)

    fun getCoolDownConverted():String{
        countDown?.let {
            return "${String.format("%02d",it.coolDownTime/60)}:${String.format("%02d",it.coolDownTime%60)}"
        }
        return "00:00"
    }

    fun start(){
        if(countDown==null){
            countDown=CountDown(this,time)
        }

        PhaseManager.currentPhase=this
        starter(this)
        countDown!!.runTaskTimer(main,0,20)


    }

    fun cancel(){
        countDown?.let {
            it.cancel()
            countDown=null
        }
    }


    companion object{
        fun dummyPhase(main: KotlinTestMain):Phase{
            return Phase(main,"waiting",0,"",
                {},
                {}
            )
        }

        fun dummyPhase(main: KotlinTestMain,name: String,time: Int):Phase{
            return Phase(main,name,time,"",
                {},
                {}
            )
        }
    }
}