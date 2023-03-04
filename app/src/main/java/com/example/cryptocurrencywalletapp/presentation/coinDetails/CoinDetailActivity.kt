package com.example.cryptocurrencywalletapp.presentation.coinDetails

import android.content.Context
import android.content.Intent
import android.os.Build
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
import com.example.cryptocurrencywalletapp.presentation.walletList.WalletDialogFragment
import com.example.cryptocurrencywalletapp.presentation.walletList.WalletListState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class CoinDetailActivity : BaseActivity() {
    override fun getBottomIcon(): Int = R.id.coinListActivity
    private val coinData by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_USE_CASE_CATEGORY, Coin::class.java)
        } else {
            intent.getParcelableExtra<Coin>(EXTRA_USE_CASE_CATEGORY)
        }
    }
    private val viewModel: CoinDetailViewModel by viewModels()

    private lateinit var binding: ActivityCoinDetailBinding


    override fun getKeyBoard(): Boolean = false
    private lateinit var coinSymbol: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getCoinDetails(coin = coinData!!)
                viewModel.currentCoinData.observe(this@CoinDetailActivity, Observer {
                    renderCoins(it)
                })
            }
        }



        binding.buttonAddCoinToWallet.setOnClickListener { getWalletsAndCoins() }
        setContentView(binding.root)
    }

    private fun renderCoins(uiState: CoinDetailState){
        when(uiState){
        is CoinDetailState.Loading -> {
            //add progress bar
        }
        is CoinDetailState.Success ->{
            coinSymbol = uiState.coinExchange.coin.symbol
            binding.textViewName.text = uiState.coinExchange.coin.name
            binding.textViewCHY.text = uiState.coinExchange.rates[0].rate.toString()

        } is CoinDetailState.Error ->{
            binding.textViewName.text = uiState.error

        }


    }
}

    private fun getWalletsAndCoins(){
        viewModel.getUserWallets()
        viewModel.currentUserWallets.observe(this@CoinDetailActivity, Observer{
            renderWallets(it)
        })

    }
    private fun renderWallets(uiState: WalletListState){
        when(uiState){
            is WalletListState.Loading -> {
                //add progress bar
            }
            is WalletListState.Success ->{
                val dialogFragment = AddCoinToWalletDialogFragment.newInstance(uiState.coinList, coinSymbol)
                dialogFragment.show(supportFragmentManager, "ADD_COINS")

            } is WalletListState.Error ->{
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