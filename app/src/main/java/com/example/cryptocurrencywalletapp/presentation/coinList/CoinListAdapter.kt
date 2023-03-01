package com.example.cryptocurrencywalletapp.presentation.coinList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.databinding.RecyclerviewItemCoinBinding
import com.example.cryptocurrencywalletapp.domain.model.Coin
import java.text.NumberFormat

class CoinListAdapter(private val onSelect: (Coin) -> Unit) : RecyclerView.Adapter<CoinListAdapter.ViewHolder>(), Filterable {
    private val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
    var allCoinList: List<Coin>? = null
    var coinList: MutableList<Coin>? = null
    fun addData(list: List<Coin>) {
        allCoinList = list as List<Coin>
        coinList =  allCoinList!!.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = RecyclerviewItemCoinBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_coin, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coinList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.binding){
        val coin = coinList?.get(position) ?: return@with
        rank.text = coin.symbol
        name.text = coin.name
        val currentPriceFormatted: String = formatter.format(coin.price)
        currentPrice.text = currentPriceFormatted
        recyclerItem.setOnClickListener{
                onSelect(coinList!![position])
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) coinList  = allCoinList?.toMutableList() else {
                    val filteredList =  mutableListOf<Coin>()
                    allCoinList
                        ?.filter {
                            (it.symbol.contains(constraint!!)) or
                                    (it.name.contains(constraint))

                        }
                        ?.forEach {

                                filteredList.add(it)
                            }

                    coinList = filteredList

                }
                return FilterResults().apply { values = coinList}
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                coinList  = if (results?.values == null)
                    mutableListOf<Coin>()
                else
                    results.values as MutableList<Coin>
                notifyDataSetChanged()
            }

            }
        }
    }

