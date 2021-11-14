package gst.trainingcourse.service_coroutine

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import gst.trainingcourse.service_coroutine.Service.CalculateService
import gst.trainingcourse.service_coroutine.Service.ICalculate

class MainActivity : AppCompatActivity() {

    private var mBound =false
    private lateinit var mService : ICalculate
    private val connection =object :ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
          mBound =true
            val binder = service as CalculateService.LocalBinder
            mService = binder.service
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
          mBound =false
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val btnCalculate : Button = findViewById(R.id.btnCalculate)
        btnCalculate.setOnClickListener {
            calculate()
        }
    }

    private fun calculate() {
        if(mBound){
            mService.Calculate {
                result -> showDialog(result)
            }
        }
    }

    private fun showDialog(result:Long){
        val alertDialog = AlertDialog.Builder(this).apply {
            setTitle(TITLE)
            setMessage("$Message $result")
            setNegativeButton("OK"){_,_ ->}
        }.create()
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        Intent(this,CalculateService::class.java).also {
            intent -> bindService(intent,connection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound =false
    }

    companion object{
        private const val TITLE ="Calculate finished !"
        private const val Message ="Result is :"
    }
}