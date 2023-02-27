package com.example.cryptocurrencywalletapp.presentation.userProfile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.presentation.BaseActivity
import com.example.cryptocurrencywalletapp.presentation.CoinListActivity

class ProfileActivity : BaseActivity() {
    override fun getBottomIcon(): Int = R.id.profileActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }

    companion object {



        fun newIntent(context: Context) =
            Intent(context, ProfileActivity::class.java).apply {}
    }

}