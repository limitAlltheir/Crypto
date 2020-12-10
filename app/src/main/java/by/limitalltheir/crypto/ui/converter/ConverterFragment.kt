package by.limitalltheir.crypto.ui.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.limitalltheir.crypto.R
import by.limitalltheir.crypto.data.cryptocurrency.entity.Rates
import by.limitalltheir.crypto.ui.utils.roundToTwo
import by.limitalltheir.crypto.ui.crypto.CryptoViewModel
import by.limitalltheir.crypto.ui.crypto.MyLifeCycleObserver
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.converter_layout.*

class ConverterFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.converter_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val cryptoET = view?.findViewById<EditText>(R.id.crypto_et)
        val usdTV = view?.findViewById<TextView>(R.id.usd_tv)
        val currencyTV = view?.findViewById<TextView>(R.id.currency_tv)
        val cryptoIV = view?.findViewById<ImageView>(R.id.crypto_choice)
        val exchangeIV = view?.findViewById<ImageView>(R.id.exchange_img)
        var currency = 1.00
        cryptoET?.setText(getString(R.string.start_value_cryptoET))
        lifecycle.addObserver(MyLifeCycleObserver())
        val myViewModel = ViewModelProvider(requireActivity()).get(CryptoViewModel::class.java)

        var ratesList = emptyList<Rates>()
        var namesList = emptyArray<String>()
        var imagesList = emptyArray<String>()

        cryptoIV?.setOnClickListener {

            Toast.makeText(requireContext(), "OK", Toast.LENGTH_SHORT).show()

            myViewModel.getListRates().observe(requireActivity(), Observer { rates ->
                ratesList = rates
            })

            myViewModel.getListImages().observe(requireActivity(), Observer { images ->
                imagesList = images
            })

            myViewModel.getListNames().observe(requireActivity(), Observer { names ->
                namesList = names
            })

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.choose_crypto))
                .setNeutralButton(getString(R.string.cancelButton)) { dialogInterface, _ ->
                    dialogInterface.cancel()
                }
                .setSingleChoiceItems(
                    namesList,
                    -1
                ) { dialogInterface, i ->
                    currency = ratesList[i].price
                    currencyTV?.text = getString(R.string.one_with_space).plus(namesList[i]).plus(getString(
                                            R.string.equals_around_with_spaces)).plus(
                        currency.roundToTwo().toString().plus(getString(R.string.dollar))
                    )
                    Picasso.get().load(imagesList[i]).into(crypto_choice)
                    dialogInterface?.dismiss()
                }
                .show()
        }

        exchangeIV?.setOnClickListener {

            cryptoET?.setText(usdTV?.text)
        }

        cryptoET?.doAfterTextChanged {
            if(it.toString().isNotEmpty()) {
                usdTV?.text = "${(it.toString().toDouble() * currency).roundToTwo()}"
            } else usdTV?.text = getString(R.string.zero_value_usdTV)
        }
    }
}