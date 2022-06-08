package com.poc.ct.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
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
}