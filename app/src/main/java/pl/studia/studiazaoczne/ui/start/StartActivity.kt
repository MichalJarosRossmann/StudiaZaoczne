package pl.studia.studiazaoczne.ui.start

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import pl.studia.studiazaoczne.R
import pl.studia.studiazaoczne.databinding.ActivityMainBinding
import pl.studia.studiazaoczne.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    val binding: ActivityStartBinding by lazy { ActivityStartBinding.inflate(layoutInflater) }

    val viewModel by viewModels<StartViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            viewModel.setText("aaa")
        }

//        findViewById<TextView>(R.id.textView)

        viewModel.textLiveData.observe(this, Observer {

            when(it){
                is StartViewModel.ViewState.Error -> TODO()
                is StartViewModel.ViewState.Loading -> showLoading()
                is StartViewModel.ViewState.ShowText -> showText(it)
            }

        })
    }

    private fun showLoading() {
        binding.progressBar.visibility=View.VISIBLE

    }

    private fun showText(it: StartViewModel.ViewState.ShowText) {
        binding.progressBar.visibility=View.GONE
        binding.textView.text=it.text
    }
}