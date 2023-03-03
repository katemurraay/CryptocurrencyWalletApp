package com.example.cryptocurrencywalletapp.presentation.userProfile

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.cryptocurrencywalletapp.CryptoApplication
import com.example.cryptocurrencywalletapp.databinding.FragmentUpdateUserDialogBinding
import com.example.cryptocurrencywalletapp.domain.model.User
import com.example.cryptocurrencywalletapp.utils.toEditable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.sql.Timestamp

@AndroidEntryPoint
class UpdateUserDialogFragment : DialogFragment() {
    private var _binding: FragmentUpdateUserDialogBinding? = null

    private val binding get() = _binding!!
    private val viewModel: UpdateUserViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentUpdateUserDialogBinding.inflate(layoutInflater)
        populateForm()

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
        binding.buttonUpdate.setOnClickListener {
            if(validateForm()){
                val updateUser = updateUser()
                lifecycleScope.launch {
                    viewModel.onUpdateUser(updateUser)
                    viewModel.updatedUserData.observe(this@UpdateUserDialogFragment, Observer {
                        validateRegistration(it)
                    })

                }
            }}





        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()

    }

    private fun populateForm(){
        binding.editTextUsername.text = CryptoApplication.user?.username?.toEditable()
        binding.editTextPassword.text = CryptoApplication.user?.password?.toEditable()
        binding.editTextFirstName.text = CryptoApplication.user?.firstName?.toEditable()
        binding.editTextLastName.text = CryptoApplication.user?.surname?.toEditable()
        binding.editTextEmail.text = CryptoApplication.user?.email?.toEditable()
        binding.editTextImage.text = CryptoApplication.user?.image?.toEditable()

    }
    private fun validateRegistration(state: UpdateUserState) {
        when (state) {
            is UpdateUserState.Loading -> {
                //add progress bar
            }
            is UpdateUserState.Success -> {
                (activity as ProfileActivity).getUserDetails()
                dismiss()
            }
            is UpdateUserState.Error -> {
                binding.editTextUsername.text = state.error.toEditable()
            }
        }
    }


    private fun updateUser(): User {
        val password = binding.editTextPassword.text.toString()
        val username = binding.editTextUsername.text.toString()
        val firstName = binding.editTextFirstName.text.toString()
        val surname = binding.editTextLastName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val image = binding.editTextImage.text.toString()
        return User(
            userId = CryptoApplication.user?.userId,
            password = password,
            username = username,
            firstName = firstName,
            email = email,
            image = image,
            surname = surname,
            joined = Timestamp(System.currentTimeMillis()).toString()
        )
    }

    private fun validateForm(): Boolean{
        return !(binding.editTextPassword.text.toString()
            .isEmpty() || binding.editTextUsername.text.toString()
            .isEmpty() || binding.editTextFirstName.text.toString()
            .isEmpty() || binding.editTextImage.text.toString()
            .isEmpty() || binding.editTextLastName.text.toString()
            .isEmpty() || binding.editTextEmail.text.toString().isEmpty())
    }
    }



