package com.example.cryptocurrencywalletapp.presentation.userLogin

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.databinding.ActivityLoginBinding
import com.example.cryptocurrencywalletapp.presentation.coinList.CoinListActivity
import com.example.cryptocurrencywalletapp.presentation.userRegister.RegisterActivity
import com.example.cryptocurrencywalletapp.utils.setGone
import com.example.cryptocurrencywalletapp.utils.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fromBottom = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom)
        binding.relativeLayoutSplash.animate().translationY(-1700f).setDuration(800).startDelay = 2000;

        binding.linearLayoutLogin.startAnimation(fromBottom)
        binding.buttonLogin.setOnClickListener {
            if(validateForm()) {
                val password = binding.editTextPassword.text.toString()
                val username = binding.editTextUsername.text.toString()

                lifecycleScope.launch {

                        viewModel.onValidateLogin(username = username, password = password)
                        viewModel.loginData.observe(this@LoginActivity, Observer {
                        validateCredentials(it)
                    })

            }
       }}
        binding.textViewRegister.setOnClickListener{
            val intent: Intent = RegisterActivity.newIntent(applicationContext)
            startActivity(intent)
        }

    }
    private fun validateCredentials(state: LoginState){
        when(state){
            is LoginState.Loading ->{
                binding.textViewError.setGone()
            }
            is LoginState.Success ->{


                val intent: Intent = CoinListActivity.newIntent(applicationContext)
                startActivity(intent)
                finish()

            }
            is LoginState.Error ->{
                binding.textViewError.text = state.error
                binding.textViewError.setVisible()
                binding.editTextUsername.setText("")
                binding.editTextPassword.setText("")

            }
        }
    }


    private fun validateForm(): Boolean{
        return !(binding.editTextPassword.text.toString().isEmpty()|| binding.editTextUsername.text.toString().isEmpty())
    }


}