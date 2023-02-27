package com.example.cryptocurrencywalletapp.presentation.coinDetails

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.databinding.ActivityCoinDetailBinding
import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.presentation.BaseActivity

class CoinDetailActivity : BaseActivity() {
    private val coinData by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_USE_CASE_CATEGORY, Coin::class.java)
        } else {
            intent.getParcelableExtra<Coin>(EXTRA_USE_CASE_CATEGORY)
        }
    }

    private lateinit var binding: ActivityCoinDetailBinding
    override fun getBottomIcon(): Int = R.id.coinListActivity

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        binding.textViewName.text = coinData?.name
        binding.textViewCHY.text = coinData?.price.toString()

    }

    companion object {

        private const val EXTRA_USE_CASE_CATEGORY = "EXTRA_USE_CASES"

        fun newIntent(context: Context, coin: Coin) =
            Intent(context, CoinDetailActivity::class.java).apply {
                putExtra(EXTRA_USE_CASE_CATEGORY, coin)
            }
    }
}