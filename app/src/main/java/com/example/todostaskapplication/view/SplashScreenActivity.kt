package com.example.todostaskapplication.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.todostaskapplication.R
import com.example.todostaskapplication.databinding.ActivitySplashScreenBinding
import com.example.todostaskapplication.utils.Coroutines
import com.example.todostaskapplication.view.viewmodels.MainViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivitySplashScreenBinding.inflate(layoutInflater)
     setContentView(binding.root)

        Coroutines.main {
            viewModel.getAllTodosApi().observe(this@SplashScreenActivity){todoRes->
                if(todoRes!=null){
                    Coroutines.main {
                        startActivity(Intent(this,MainActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                }
            }
        }
    }

}