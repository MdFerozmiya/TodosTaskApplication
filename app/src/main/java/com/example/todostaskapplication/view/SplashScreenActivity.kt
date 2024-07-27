package com.example.todostaskapplication.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.todostaskapplication.R
import com.example.todostaskapplication.TodosTaskApplication
import com.example.todostaskapplication.databinding.ActivitySplashScreenBinding
import com.example.todostaskapplication.utils.Coroutines
import com.example.todostaskapplication.utils.NetWorkManager
import com.example.todostaskapplication.utils.showToastLong
import com.example.todostaskapplication.view.viewmodels.MainViewModel
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private val viewModel: MainViewModel by viewModels()
    private lateinit var networkManager :NetWorkManager
    private lateinit var dialog : Dialog

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivitySplashScreenBinding.inflate(layoutInflater)
     setContentView(binding.root)
        dialog= Dialog(this,R.style.customDialogue)
        networkManager = NetWorkManager(this)
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        val networkReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                networkManager.observe(this@SplashScreenActivity) {
                    val todosData = viewModel.getAllTodos()
                    // If todos table is empty we are calling api otherwise directly navigate to home page
                    if (todosData.isEmpty()) {
                        if (it) {
                            if (dialog.isShowing){
                                dialog.dismiss()
                            }
                            Coroutines.main {
                                viewModel.getAllTodosApi()
                                    .observe(this@SplashScreenActivity) { todoRes ->
                                        if (todoRes != null) {
                                            val time:Long = 2000
                                            Coroutines.main {
                                                delay(time)
                                                navigateToHomePage()
                                            }

                                        }
                                    }
                            }
                        } else {
//                            showNoInternetPopUp()
                            navigateToHomePage()
                        }
                    } else{
                        navigateToHomePage()
                    }
                }
            }
        }
        registerReceiver(networkReceiver, filter)


    }
private fun navigateToHomePage(){
    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
    overridePendingTransition(
        R.anim.slide_in_right,
        R.anim.slide_out_left
    )
}
private fun showNoInternetPopUp() {

    val dialogView = LayoutInflater.from(this)
        .inflate(R.layout.no_internet_pop_up, null, false)
    dialog.setCancelable(false)
    dialog.setContentView(dialogView)
    val window = dialog.window
    if (window!=null) {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = layoutParams
    }
    dialog.show()

}
}