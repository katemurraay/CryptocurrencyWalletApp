package com.example.cryptocurrencywalletapp.presentation

import android.content.Context
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.presentation.coinDetails.CoinDetailActivity
import com.example.cryptocurrencywalletapp.presentation.userProfile.ProfileActivity
import com.example.cryptocurrencywalletapp.presentation.userWallet.WalletActivity
import com.example.cryptocurrencywalletapp.utils.setGone
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

abstract class BaseActivity : AppCompatActivity() {
    abstract fun getBottomIcon(): Int
   private fun setBottomIcon(id: Int){
        getBottomNavigation().selectedItemId = id
    }
    private fun getBottomNavigation(): BottomNavigationView = findViewById(R.id.bottom_navigation)
    override fun onStart() {
        super.onStart()
        setBottomIcon(getBottomIcon())
        getBottomNavigation().setOnItemSelectedListener{

                when(it.itemId){
                    R.id.profileActivity->startBottomNavigationActivity("Profile", this)
                    R.id.walletActivity -> startBottomNavigationActivity("Wallet", this)
                    R.id.coinListActivity -> startBottomNavigationActivity("Coin", this)
                }
                 true;
            }
        }



    private fun startBottomNavigationActivity(strActivity: String, applicationContext: Context){
        var intent: Intent? = null
        when(strActivity) {
            "Profile" -> intent = ProfileActivity.newIntent(applicationContext)
            "Coin" -> intent = CoinListActivity.newIntent(applicationContext)
            "Wallet" -> intent = WalletActivity.newIntent(applicationContext)
        }
        if (intent !=null)  {
            startActivity(intent)
            finish()
        }
    }
    }





