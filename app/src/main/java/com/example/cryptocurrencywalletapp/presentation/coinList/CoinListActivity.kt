package com.example.cryptocurrencywalletapp.presentation.coinList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.*
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.databinding.ActivityCoinListBinding
import com.example.cryptocurrencywalletapp.domain.model.Coin
import com.example.cryptocurrencywalletapp.presentation.BaseActivity
import com.example.cryptocurrencywalletapp.presentation.coinDetails.CoinDetailActivity
import com.example.cryptocurrencywalletapp.utils.setGone
import com.example.cryptocurrencywalletapp.utils.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CoinListActivity : BaseActivity(), SearchView.OnQueryTextListener {
    private val viewModel: CoinListViewModel by viewModels()
    private lateinit var binding: ActivityCoinListBinding
    private lateinit var adapter: CoinListAdapter
    override fun getBottomIcon(): Int = R.id.coinListActivity
    override fun getAnimationFile(): Int = R.raw.crypto_coins

    override fun getKeyBoard(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CoinListAdapter(onSelectClickListener)
        binding = ActivityCoinListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter
        binding.searchView.setOnQueryTextListener(this)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.currentCoinData.observe(this@CoinListActivity, Observer {
                    render(it)
                })
            }
        }





    }



    private fun render(state: CoinListState){
        when (state){
        is CoinListState.Loading -> {
            binding.progressBar.setVisible()
            binding.recyclerView.setGone()
            binding.searchView.setGone()
            binding.textViewResult.text = ""
        }
            is CoinListState.Success ->{
                binding.progressBar.setGone()
                binding.searchView.setVisible()
                binding.recyclerView.setVisible()
                binding.textViewResult.setGone()
                adapter.addData(state.coinList)
            } is CoinListState.Error ->{
                binding.searchView.setGone()
                binding.textViewResult.setVisible()
                binding.progressBar.setGone()
                binding.recyclerView.setGone()
                binding.textViewResult.text = state.error
            }


    }
    }
        private val onSelectClickListener: (Coin) -> Unit = { clickedCoin ->
            val intent = CoinDetailActivity.newIntent(applicationContext, clickedCoin)
            startActivity(intent)
        }


    override fun onQueryTextSubmit(query: String?): Boolean {

        adapter.filter.filter(query)
        return false
    }
    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

    companion object {
         fun newIntent(context: Context) =
            Intent(context, CoinListActivity::class.java).apply {

            }
    }
}



