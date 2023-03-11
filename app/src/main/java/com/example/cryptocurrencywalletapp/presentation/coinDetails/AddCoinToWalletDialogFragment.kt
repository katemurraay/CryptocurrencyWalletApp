package com.example.cryptocurrencywalletapp.presentation.coinDetails

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.databinding.FragmentAddCoinToWalletDialogBinding
import com.example.cryptocurrencywalletapp.domain.model.Wallet

import com.example.cryptocurrencywalletapp.utils.IConstants.ADD_COIN_TO_WALLET_COIN_SYMBOL
import com.example.cryptocurrencywalletapp.utils.IConstants.ADD_COIN_TO_WALLET_WALLETS
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class AddCoinToWalletDialogFragment : DialogFragment() {

    private var userWallets: ArrayList<Wallet>? = null
    private var coinSymbol: String? =null
    private var _binding: FragmentAddCoinToWalletDialogBinding? = null
    private lateinit var adapter: ArrayAdapter<Wallet>
    private val binding get() = _binding!!
    private val viewModel: AddCoinToWalletViewModel by viewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        _binding = FragmentAddCoinToWalletDialogBinding.inflate(layoutInflater)
        arguments?.let {
            userWallets = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelableArrayList(ADD_COIN_TO_WALLET_WALLETS, Wallet::class.java)
            } else {
                it.getParcelableArrayList(ADD_COIN_TO_WALLET_WALLETS)
            }
            coinSymbol = it.getString(ADD_COIN_TO_WALLET_COIN_SYMBOL)
        }
        if (userWallets.isNullOrEmpty()){
            dismiss()
        } else{

            adapter = ArrayAdapter(requireContext(), R.layout.spinner_item_wallet,
                userWallets!!)
            binding.spinnerUserWallets.adapter = adapter
        }

        binding.buttonCancelCoinToWallet.setOnClickListener { dismiss() }
        binding.buttonAddCoinToWallet.setOnClickListener { addCoinToWallet() }

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    private fun addCoinToWallet(){
       val wallet =  binding.spinnerUserWallets.selectedItem as Wallet
       viewModel.onAddCoinToWallet(coinSymbol!!, wallet.id!!)
        viewModel.insertCoinToWalletData.observe(this@AddCoinToWalletDialogFragment, Observer {
            renderOutput(it)
        })
    }

    private fun renderOutput(state: AddCoinToWalletState){
        when (state) {
            is AddCoinToWalletState.Success -> {
                dismiss()
            }
            is AddCoinToWalletState.Error -> {
                binding.textViewError.text = state.error
            }
            is AddCoinToWalletState.Loading ->{

            }
        }
    }
    companion object {

        @JvmStatic
        fun newInstance(wallets: List<Wallet>, coinSymbol: String) =
            AddCoinToWalletDialogFragment().apply {
                arguments = Bundle().apply {
                    val arrayWallets = ArrayList(wallets)
                    putParcelableArrayList(ADD_COIN_TO_WALLET_WALLETS, arrayWallets)
                    putString(ADD_COIN_TO_WALLET_COIN_SYMBOL, coinSymbol)
                }
            }
    }
}