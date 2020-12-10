package by.limitalltheir.crypto.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.limitalltheir.crypto.R
import by.limitalltheir.crypto.data.cryptocurrency.entity.Rates
import by.limitalltheir.crypto.ui.adapters.CryptoAdapter
import by.limitalltheir.crypto.ui.crypto.CryptoViewModel
import by.limitalltheir.crypto.ui.crypto.MyLifeCycleObserver
import by.limitalltheir.crypto.ui.utils.isFiltered
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import okhttp3.internal.notifyAll

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNavigation.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        val topBar = findViewById<MaterialToolbar>(R.id.tool_bar)
        topBar.setupWithNavController(navController, appBarConfiguration)
        var namesList = emptyArray<String>()
        val filteredList = emptyList<Rates>().toMutableList()

        lifecycle.addObserver(MyLifeCycleObserver())
        val myViewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)

        myViewModel.getListNames().observe(this, Observer { names ->
            namesList = names
        })
        topBar?.setOnMenuItemClickListener { it ->
            when (it.itemId) {
                R.id.filter -> {
                    filteredList.clear()

                    MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.choose_crypto))
                        .setNeutralButton(getString(R.string.cancelButton)) { dialogInterface, _ ->
                            dialogInterface.cancel()
                        }
                        .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                            myViewModel.listFiltered(filteredList)
                            isFiltered = true
                            dialog.dismiss()
                        }
                        .setMultiChoiceItems(namesList, null) { _, which, isChecked ->
                            if(isChecked) {
                                myViewModel.getListRates()
                                    .observe(this, Observer { list ->
                                        filteredList += list[which]
                                    })
                            }
                        }
                        .show()

                    true
                } else -> false
            }
        }
    }
}