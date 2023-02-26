package com.example.cryptocurrencywalletapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.databinding.ActivityMainBinding
import com.example.cryptocurrencywalletapp.presentation.coinList.CoinListAdapter
import com.example.cryptocurrencywalletapp.presentation.coinList.CoinListState
import com.example.cryptocurrencywalletapp.presentation.coinList.CoinListViewModel
import com.example.cryptocurrencywalletapp.utils.Resource
import com.example.cryptocurrencywalletapp.utils.setGone
import com.example.cryptocurrencywalletapp.utils.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), SearchView.OnQueryTextListener {
    private val viewModel: CoinListViewModel by viewModels()
    private val adapter = CoinListAdapter()
    private lateinit var binding: ActivityMainBinding
    override fun getToolbarTitle() = "Crypto APP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter
        binding.searchView.setOnQueryTextListener(this)
        viewModel.currentStockPriceAsLiveData.observe(this) { uiState ->
            if (uiState != null) {
                render(uiState)
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
                binding.progressBar.setGone()
                binding.recyclerView.setGone()
                binding.textViewResult.text = state.error
            }


    }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }
    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

}



