package com.example.cryptocurrencywalletapp.presentation.walletList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.databinding.RecyclerviewItemWalletBinding
import com.example.cryptocurrencywalletapp.domain.model.Wallet

class WalletListAdapter(private val onSelect: (Wallet) -> Unit) : RecyclerView.Adapter<WalletListAdapter.ViewHolder>(){

    var allWalletList: List<Wallet>? = null
    var walletList: MutableList<Wallet>? = null
    private var colors = listOf<String>("#fed800","#00c1fe", "#b29700","#0087b2","#fe0093")
    fun addData(list: List<Wallet>) {
        allWalletList = list as List<Wallet>
        walletList =  allWalletList!!.toMutableList()
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         val binding = RecyclerviewItemWalletBinding.bind(view)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_wallet, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return walletList?.size ?: 0
    }

    fun removeItem(position: Int) {
        walletList?.removeAt(position)
        notifyItemRemoved(position)
    }



    fun getData(): MutableList<Wallet>?{
        return walletList
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.binding){
        val wallet = walletList?.get(position) ?: return@with
        textViewTitle.text = wallet.title
        val coins = wallet.coins?.map { it.name }.toString()

        val coin = coins.replace("[", "").replace("]","");
        val strCoins = "Coins: $coin"
        textViewCoins.text = strCoins
        var setColor = position % colors.size
        recyclerItemWallet.setBackgroundColor(Color.parseColor(colors[setColor]))
        recyclerItemWallet.setOnClickListener{
            onSelect(walletList!![position])
        }
    }

}