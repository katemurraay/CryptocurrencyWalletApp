package com.example.cryptocurrencywalletapp.presentation.userWallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.utils.IConstants.WALLET_DIALOG_FUNCTION


class WalletDialogFragment : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet_dialog, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            WalletDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(WALLET_DIALOG_FUNCTION, param1)
                }
            }
    }
}