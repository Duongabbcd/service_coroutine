package gst.trainingcourse.service_coroutine.Service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.*

class CalculateService :Service() ,ICalculate{
    private val coroutineScope = CoroutineScope(Dispatchers.Default + Job()) ;
    private val binder = LocalBinder()

    inner class LocalBinder:Binder(){
        val service :CalculateService
        get() = this@CalculateService
    }

    override fun onBind(p0: Intent?): IBinder? =binder

    override fun Calculate(onFinish: (Long) -> Unit) {
      coroutineScope.launch {
          var sum =0L
          for(number in 1..1000000) sum+= number
          delay(5000)
          withContext(Dispatchers.Main){
              onFinish.invoke(sum)
          }
      }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}