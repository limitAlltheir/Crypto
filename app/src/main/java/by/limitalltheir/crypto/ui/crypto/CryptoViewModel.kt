package by.limitalltheir.crypto.ui.crypto

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.limitalltheir.crypto.ui.utils.URL_IMG
import by.limitalltheir.crypto.data.cryptocurrency.entity.Rates
import by.limitalltheir.crypto.data.cryptocurrency.mapper.RatesMapper
import by.limitalltheir.crypto.data.cryptocurrency.retrofit.RetrofitFactory
import by.limitalltheir.crypto.ui.utils.launchIo

class CryptoViewModel : ViewModel() {

    private val retrofit = RetrofitFactory().getRetrofit()
    private var cryptoList: MutableLiveData<List<Rates>> = MutableLiveData()
    private var cryptoListFiltered: MutableLiveData<List<Rates>> = MutableLiveData()
    private var cryptoImageList: MutableLiveData<Array<String>> = MutableLiveData()
    private var cryptoNamesList: MutableLiveData<Array<String>> = MutableLiveData()


    init {

        launchIo {
            val response = retrofit.getRatesAsync().await()

            if (response.isSuccessful) {

                val ratesResponse = response.body()
                val rates = ratesResponse?.dataList?.map { RatesMapper().map(it) }
                var ratesName = emptyArray<String>()
                var ratesImage = emptyArray<String>()
                if (rates != null) {
                    for (rate in rates) {
                        ratesName += rate.name
                        ratesImage += "$URL_IMG${rate.id}.png"
                    }
                }
                cryptoNamesList.postValue(ratesName)
                cryptoImageList.postValue(ratesImage)
                cryptoList.postValue(rates)
            }
        }
    }

    fun getListNames() = cryptoNamesList

    fun listFiltered(list: List<Rates>) {
        cryptoListFiltered.postValue(list)
    }

    fun getListImages() = cryptoImageList

    fun getListRates() = cryptoList

    fun getListRatesFiltered() = cryptoListFiltered
}