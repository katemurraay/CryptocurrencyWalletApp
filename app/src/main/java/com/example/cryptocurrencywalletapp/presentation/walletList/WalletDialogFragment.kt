package com.example.cryptocurrencywalletapp.presentation.walletList

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.cryptocurrencywalletapp.CryptoApplication
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.databinding.FragmentWalletDialogBinding
import com.example.cryptocurrencywalletapp.domain.model.Wallet
import com.example.cryptocurrencywalletapp.presentation.userProfile.ProfileActivity
import com.example.cryptocurrencywalletapp.utils.IConstants.WALLET_DIALOG_FUNCTION
import com.example.cryptocurrencywalletapp.utils.IConstants.WALLET_DIALOG_WALLET_OBJ
import com.example.cryptocurrencywalletapp.utils.toEditable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletDialogFragment : DialogFragment() {
    private var _binding: FragmentWalletDialogBinding? = null

    private val binding get() = _binding!!
    private var isUpdate: Boolean = false
    private var strFunction: String? = null
    private var wallet: Wallet? = null

    private val viewModel: WalletDialogViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentWalletDialogBinding.inflate(layoutInflater)
        arguments?.let {
            isUpdate = (it.getInt(WALLET_DIALOG_FUNCTION) == 1)
            if (isUpdate) {
                wallet = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(WALLET_DIALOG_WALLET_OBJ, Wallet::class.java)
                } else {
                    it.getParcelable(WALLET_DIALOG_WALLET_OBJ)
                }
            }
        }

        if (isUpdate){
            populateUpdateView()
        }else{
            populateInsertView()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

        binding.buttonAddOrUpdate.setOnClickListener {
            lifecycleScope.launch {
                val strTitle = binding.editTextTitle.text.toString()
                if (isUpdate) {

                    if (validateForm()){
                    viewModel.onUpdateWallet(wallet?.id!!,strTitle, CryptoApplication.user?.userId!!)
                    viewModel.updatedWalletData.observe(this@WalletDialogFragment, Observer {
                        validateWalletDBTransaction(it)
                    })
                    }
                }
                else{
                    if (validateForm()){
                    val insertWallet = Wallet(id = null, title = strTitle, userCreatorId = CryptoApplication.user?.userId!!, coins = emptyList())
                    viewModel.onInsertWallet(insertWallet)
                    viewModel.insertWalletData.observe(this@WalletDialogFragment, Observer {
                        validateWalletDBTransaction(it)
                    })
                }
                }
            }
        }

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()

    }

    private fun validateForm(): Boolean{
        return (binding.editTextTitle.toString().isNotEmpty())
    }

    private fun populateUpdateView(){
        binding.buttonAddOrUpdate.text = getString(R.string.btn_update)
        binding.editTextTitle.text = wallet!!.title.toString().toEditable()
    }

    private fun populateInsertView(){
        binding.buttonAddOrUpdate.text = getString(R.string.btn_insert)
        binding.editTextTitle.text = null
    }

    private fun  validateWalletDBTransaction(state: WalletDialogState) {
        when (state) {
            is WalletDialogState.Loading -> {
                //add progress bar
            }
            is WalletDialogState.Success -> {
                (activity as WalletListActivity).addWalletLiveData()
                dismiss()
            }
            is WalletDialogState.Error -> {
                binding.textViewError.text = state.error
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int, wallet: Wallet?) =
            WalletDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(WALLET_DIALOG_FUNCTION, param1)
                    putParcelable(WALLET_DIALOG_WALLET_OBJ, wallet)

                }
            }
    }
}