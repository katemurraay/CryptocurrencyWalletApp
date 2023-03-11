package com.example.cryptocurrencywalletapp.presentation.userRegister

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.cryptocurrencywalletapp.databinding.ActivityRegisterBinding
import com.example.cryptocurrencywalletapp.domain.model.User
import com.example.cryptocurrencywalletapp.presentation.coinList.CoinListActivity
import com.example.cryptocurrencywalletapp.presentation.userLogin.LoginActivity
import com.example.cryptocurrencywalletapp.utils.setGone
import com.example.cryptocurrencywalletapp.utils.setVisible
import com.example.cryptocurrencywalletapp.utils.toEditable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.sql.Timestamp

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textViewLogin.setOnClickListener {
            val intent: Intent = LoginActivity.newIntent(applicationContext)
            startActivity(intent)
        }
        binding.textViewError.setGone()
        binding.buttonRegister.setOnClickListener {
            if (validateForm()) {
                val password = binding.editTextPassword.text.toString()
                val username = binding.editTextUsername.text.toString()
                val firstName = binding.editTextFname.text.toString()
                val surname = binding.editTextSurname.text.toString()
                val email = binding.editTextEmail.text.toString()
                val user = User(
                    userId = null,
                    password = password,
                    username = username,
                    firstName = firstName,
                    email = email,
                    surname = surname,
                    joined = Timestamp(System.currentTimeMillis()).toString()
                )
                lifecycleScope.launch {

                        viewModel.onValidateRegistration(user)
                        viewModel.registerData.observe(this@RegisterActivity, Observer {
                            validateRegistration(it)
                        })

                }
            }}



    }

    companion object {
        fun newIntent(context: Context) =
            Intent(context, RegisterActivity::class.java).apply {}
    }

    private fun validateForm(): Boolean {
        return !(binding.editTextPassword.text.toString()
            .isEmpty() || binding.editTextUsername.text.toString()
            .isEmpty() || binding.editTextFname.text.toString()
            .isEmpty() || binding.editTextSurname.text.toString()
            .isEmpty() || binding.editTextEmail.text.toString().isEmpty())
    }

    private fun validateRegistration(state: RegistrationState) {
        when (state) {
            is RegistrationState.Loading -> {
                //add progress bar
            }
            is RegistrationState.Success -> {
                binding.textViewError.setGone()
                val intent = CoinListActivity.newIntent(applicationContext)
                startActivity(intent)
                finish()
            }
            is RegistrationState.Error -> {
                    binding.textViewError.setVisible()
                    binding.textViewError.text = state.error
            }
        }
    }
}
