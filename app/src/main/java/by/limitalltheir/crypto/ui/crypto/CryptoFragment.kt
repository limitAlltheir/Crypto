package by.limitalltheir.crypto.ui.crypto

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.limitalltheir.crypto.R
import by.limitalltheir.crypto.data.cryptocurrency.entity.Rates
import by.limitalltheir.crypto.ui.adapters.CryptoAdapter
import by.limitalltheir.crypto.ui.utils.isCardSelected
import by.limitalltheir.crypto.ui.utils.isFiltered
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class CryptoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCardSelected = false
        return inflater.inflate(R.layout.activity_crypto, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val cryptoAdapter = CryptoAdapter()
        val rv = view?.findViewById<RecyclerView>(R.id.recycler_view)
        val progressWheel = view?.findViewById<ProgressBar>(R.id.progress_wheel)

        rv?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cryptoAdapter
            hasFixedSize()
        }

        lifecycle.addObserver(MyLifeCycleObserver())
        val myViewModel = ViewModelProvider(requireActivity()).get(CryptoViewModel::class.java)

        if(isFiltered) {
            myViewModel.getListRatesFiltered().observe(requireActivity(), Observer {
                progressWheel?.isVisible = false
                cryptoAdapter.setList(it)
            })
        } else {
            myViewModel.getListRates().observe(requireActivity(), Observer {
                progressWheel?.isVisible = false
                cryptoAdapter.setList(it)
            })
        }
    }
}