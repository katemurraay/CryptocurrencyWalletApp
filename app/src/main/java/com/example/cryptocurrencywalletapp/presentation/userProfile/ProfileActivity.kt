package com.example.cryptocurrencywalletapp.presentation.userProfile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.cryptocurrencywalletapp.CryptoApplication
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.databinding.ActivityProfileBinding
import com.example.cryptocurrencywalletapp.presentation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun getBottomIcon(): Int = R.id.profileActivity
    override fun getKeyBoard(): Boolean = false
    override fun getAnimationFile(): Int = R.raw.crypto_coins
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserDetails()
        binding.buttonEditUser.setOnClickListener {
            showEditDetails()
        }

    }
    fun getUserDetails(){
        val loggedUser = CryptoApplication.getGlobalUser()
        val fName = loggedUser?.firstName
        val surname = loggedUser?.surname
        val strName = "$fName $surname"
        binding.textViewEmail.text = loggedUser?.email
        binding.textViewName.text = strName
        binding.textViewUsername.text = loggedUser?.username
        binding.textViewJoined.text = loggedUser?.joined

    }
    private fun showEditDetails(){
        val dialogFragment =UpdateUserDialogFragment()
        dialogFragment.show(supportFragmentManager, "UPDATE_USER")
    }



    companion object {
        fun newIntent(context: Context) =
            Intent(context, ProfileActivity::class.java).apply {}
    }

}