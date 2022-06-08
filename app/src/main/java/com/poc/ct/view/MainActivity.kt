package com.poc.ct.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.poc.ct.R
import com.poc.ct.viewmodel.KanyeWestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val  viewModel : KanyeWestViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleFirebase()
        viewModel.getQuotesViewModel()

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { value: KanyeWestViewModel.QuotesEvent ->
                when(value){
                    is KanyeWestViewModel.QuotesEvent.Success ->{
                        progressBar.visibility =View.GONE
                        kanyeWestQuote.setText(value.resultText)
                    }
                    is KanyeWestViewModel.QuotesEvent.Failure -> {
                        progressBar.visibility =View.GONE
                        kanyeWestQuote.setText(value.errorText)
                    }
                    is KanyeWestViewModel.QuotesEvent.Loading -> {
                        progressBar.visibility =View.VISIBLE
                    }
                    else -> Unit
                }
            }
        }
    }


    fun handleFirebase(){
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("CLEVERTAP", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "token "+ token
            Log.d("CLEVERTAP", msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
        Firebase.messaging.isAutoInitEnabled = true
    }
}