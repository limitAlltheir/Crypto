package by.limitalltheir.crypto.ui.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.pow
import kotlin.math.round

fun launchIo(task: suspend () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        task()
    }
}

fun launchUi(task: suspend () -> Unit) {

    CoroutineScope(Dispatchers.Main).launch {
        task()
    }
}

suspend fun <R> launchForResult(task: suspend () -> R): R? {

    return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
        task()
    }

}

fun Double.roundToTwo(): Double {

    val power = (10.0).pow(2)
    return round(this * power) / power
}

var isCardSelected = false
var isFiltered = false

const val URL_IMG = "https://s2.coinmarketcap.com/static/img/coins/64x64/"
const val KEY = "key"