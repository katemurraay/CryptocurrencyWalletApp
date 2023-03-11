package com.example.cryptocurrencywalletapp.presentation.walletList

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.databinding.ActivityWalletBinding
import com.example.cryptocurrencywalletapp.domain.model.Wallet
import com.example.cryptocurrencywalletapp.presentation.BaseActivity
import com.example.cryptocurrencywalletapp.utils.SwipeToDeleteCallback
import com.example.cryptocurrencywalletapp.utils.setGone
import com.example.cryptocurrencywalletapp.utils.setVisible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WalletListActivity :  BaseActivity() {
    private val viewModel: WalletListViewModel by viewModels()
    private lateinit var adapter: WalletListAdapter
    override fun getBottomIcon(): Int = R.id.walletActivity


    override fun getKeyBoard(): Boolean =false
    private lateinit var binding: ActivityWalletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        adapter = WalletListAdapter(onSelectClickListener)
        setContentView(binding.root)
        binding.buttonInsert.setOnClickListener {
            val dialogFragment = WalletDialogFragment()
            dialogFragment.show(supportFragmentManager, "INSERT")
        }

        binding.recyclerViewWallets.adapter = adapter

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                getWalletLiveData()
            }
        }




    }


    private fun render(state: WalletListState){
        when (state){
            is WalletListState.Loading -> {
                binding.progressBarWallet.setVisible()
                binding.recyclerViewWallets.setGone()
                binding.textViewResult.text = ""
            }
            is WalletListState.Success ->{
                binding.progressBarWallet.setGone()
                binding.recyclerViewWallets.setVisible()
                binding.textViewResult.setGone()
                adapter.addData(state.coinList)
                enableSwipeToDelete()
            } is WalletListState.Error ->{
                binding.textViewResult.setVisible()
                binding.progressBarWallet.setGone()
                binding.recyclerViewWallets.setGone()
                binding.textViewResult.text = state.error
        }

    }
    }
    private fun getWalletLiveData(){
        viewModel.currentWalletData.observe(this@WalletListActivity, Observer {
            render(it)
        })
    }
    fun addWalletLiveData(){
        viewModel.getWallets()
        getWalletLiveData()
    }

    private fun enableSwipeToDelete() {
        val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                val item:Wallet = adapter.getData()!![position]
                adapter.removeItem(position)
                removeWalletFromDB(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewWallets)
    }


    private val onSelectClickListener: (Wallet) -> Unit = { clickedWallet ->
        val dialogFragment = WalletDialogFragment.newInstance(1, clickedWallet)
        dialogFragment.show(supportFragmentManager, "UPDATE")
    }
    private fun removeWalletFromDB(wallet:Wallet){
        viewModel.removeWallet(wallet)
        getWalletLiveData()

    }
    companion object {

        fun newIntent(context: Context) =
            Intent(context, WalletListActivity::class.java).apply {}
    }

}