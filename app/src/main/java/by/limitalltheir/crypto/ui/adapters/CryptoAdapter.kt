package by.limitalltheir.crypto.ui.adapters

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.limitalltheir.crypto.R
import by.limitalltheir.crypto.data.cryptocurrency.entity.Rates
import by.limitalltheir.crypto.ui.utils.KEY
import by.limitalltheir.crypto.ui.utils.URL_IMG
import by.limitalltheir.crypto.ui.utils.isCardSelected
import by.limitalltheir.crypto.ui.utils.roundToTwo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.crypto_item.view.*

class CryptoAdapter() : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    private var cryptoListAdapter = emptyList<Rates>()

    class CryptoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(rates: Rates) {

            with(itemView) {

                this.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt(KEY, adapterPosition)
                    isCardSelected = true
                    val navController = findNavController()
                    navController.navigate(R.id.action_crypto_currency_to_detailsFragment, bundle)
                }
                Picasso.get().load(URL_IMG + rates.id.toString() + ".png").into(crypto_view)
                name_view.text = rates.name
                price_view.text = rates.price.roundToTwo().toString()
                if (isCardSelected) {
                    change_view.text = rates.change.toString()
                    if (rates.change < 0.0) {
                        change_view.setTextColor(Color.RED)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_item, parent, false)
        return CryptoViewHolder(view)
    }

    override fun getItemCount() = cryptoListAdapter.size

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(cryptoListAdapter[position])
    }

    fun setList(list: List<Rates>?) {

        if (list != null) {
            cryptoListAdapter = list
        }
        notifyDataSetChanged()
    }
}