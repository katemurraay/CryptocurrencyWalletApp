package com.example.cryptocurrencywalletapp.presentation.coinDetails

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.databinding.ActivityCoinDetailBinding
import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.presentation.BaseActivity
import com.example.cryptocurrencywalletapp.presentation.coinList.CoinListState
import com.example.cryptocurrencywalletapp.presentation.coinList.CoinListViewModel
import com.example.cryptocurrencywalletapp.utils.setGone
import com.example.cryptocurrencywalletapp.utils.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class CoinDetailActivity : BaseActivity() {
    private val coinData by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_USE_CASE_CATEGORY, Coin::class.java)
        } else {
            intent.getParcelableExtra<Coin>(EXTRA_USE_CASE_CATEGORY)
        }
    }
    private val viewModel: CoinDetailViewModel by viewModels()

    private lateinit var binding: ActivityCoinDetailBinding
    override fun getBottomIcon(): Int = R.id.coinListActivity
    override fun getAnimationFile(): Int = R.raw.crypto_coins

    override fun getKeyBoard(): Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getCoinDetails(coin = coinData!!)
                viewModel.currentCoinListData.observe(this@CoinDetailActivity, Observer {
                    render(it)
                })
            }
        }

        setContentView(binding.root)
    }

    private fun render(uiState: CoinDetailState){
        when(uiState){
        is CoinDetailState.Loading -> {
            //add progress bar
        }
        is CoinDetailState.Success ->{
            binding.textViewName.text = uiState.coinExchange.coin.name
            binding.textViewCHY.text = uiState.coinExchange.rates[0].rate.toString()

        } is CoinDetailState.Error ->{
            binding.textViewName.text = uiState.error

        }


    }
}


    companion object {

        private const val EXTRA_USE_CASE_CATEGORY = "EXTRA_USE_CASES"

        fun newIntent(context: Context, coin: Coin) =
            Intent(context, CoinDetailActivity::class.java).apply {
                putExtra(EXTRA_USE_CASE_CATEGORY, coin)
            }
    }
}