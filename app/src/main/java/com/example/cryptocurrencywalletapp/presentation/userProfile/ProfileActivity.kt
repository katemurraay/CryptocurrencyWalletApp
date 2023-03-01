package com.example.cryptocurrencywalletapp.presentation.userProfile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.presentation.BaseActivity

class ProfileActivity : BaseActivity() {
    override fun getBottomIcon(): Int = R.id.profileActivity
    override fun getKeyBoard(): Boolean = false
    override fun getAnimationFile(): Int = R.raw.crypto_coins
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }

    companion object {
        fun newIntent(context: Context) =
            Intent(context, ProfileActivity::class.java).apply {}
    }

}