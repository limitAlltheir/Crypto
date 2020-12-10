package by.limitalltheir.crypto.data.cryptocurrency.networking

import by.limitalltheir.crypto.data.cryptocurrency.dto.RatesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RatesApi {

    @GET("cryptocurrency/listings/latest")
    fun getRatesAsync() : Deferred<Response<RatesResponse>>
}