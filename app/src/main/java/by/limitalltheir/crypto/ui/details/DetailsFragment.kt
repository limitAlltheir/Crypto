package by.limitalltheir.crypto.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.limitalltheir.crypto.R
import by.limitalltheir.crypto.ui.adapters.CryptoAdapter
import by.limitalltheir.crypto.ui.crypto.CryptoViewModel
import by.limitalltheir.crypto.ui.crypto.MyLifeCycleObserver
import by.limitalltheir.crypto.ui.utils.KEY

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = arguments
        val details = bundle?.getInt(KEY, 0) ?: 0
        val cryptoAdapter = CryptoAdapter()
        val rv = view?.findViewById<RecyclerView>(R.id.recycler_view_details)

        rv?.apply {
            adapter = cryptoAdapter
            layoutManager = LinearLayoutManager(requireContext())
            hasFixedSize()
        }

        lifecycle.addObserver(MyLifeCycleObserver())
        val myViewModel = ViewModelProvider(requireActivity()).get(CryptoViewModel::class.java)

        myViewModel.getListRates().observe(requireActivity(), Observer {
            val detailsList = listOf(it[details])
            cryptoAdapter.setList(detailsList)
        })
    }
}