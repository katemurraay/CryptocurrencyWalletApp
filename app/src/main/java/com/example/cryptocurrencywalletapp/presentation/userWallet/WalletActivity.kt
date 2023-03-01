package com.example.cryptocurrencywalletapp.presentation.userWallet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.presentation.BaseActivity

class WalletActivity :  BaseActivity() {
    override fun getBottomIcon(): Int = R.id.walletActivity
    override fun getAnimationFile(): Int = R.raw.crypto_coins

    override fun getKeyBoard(): Boolean =false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
    }

    companion object {



        fun newIntent(context: Context) =
            Intent(context, WalletActivity::class.java).apply {}
    }

}