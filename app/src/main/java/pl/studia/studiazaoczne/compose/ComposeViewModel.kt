package pl.studia.studiazaoczne.compose

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import pl.studia.studiadzienne.api.ClienProvider
import pl.studia.studiadzienne.api.ClientProviderSingleton
import pl.studia.studiadzienne.api.RestaurantHelloWorldResponse
import retrofit2.Callback


class ComposeViewModel : ViewModel() {

    private val _listSizeLiveData = MutableLiveData<ComposeState>(ComposeState.ShowList(0))
    val listSizeLiveData: LiveData<ComposeState> = _listSizeLiveData

    val mutex = Mutex()

    val clienProvider = ClientProviderSingleton.restaurantApi

    init {
//        threadPresentation()
        val job = CoroutineScope(Dispatchers.Main).launch {
//            testSleepCoroutines()
            val testTwoLongOperations = testTwoLongOperationsAsync()
//            Log.i("operation", "operationResult $testTwoLongOperations")
            testMutex()
        }


    }

    fun changeListSize(listSize: Int) {

        CoroutineScope(Dispatchers.Main).launch {
            _listSizeLiveData.postValue(ComposeState.Loading())
            delay(1000)
            _listSizeLiveData.postValue(ComposeState.ShowList(listSize))
        }
    }


    fun threadPresentation() {

        val thread = Thread {
            Log.i("threadPresentation", "start")
            Thread.sleep(5000)
            Log.i("threadPresentation", "end")
        }
        thread.start()
    }

    suspend fun testSleepCoroutines() {
        Log.i("testSleepCoroutines", "start")
        delay(10000)
        Log.i("testSleepCoroutines", "end")
    }


    suspend fun testTwoLongOperations(): Int {
        Log.i("testTwoLongOperations", "start")
        val operation1 = operation1()
        val operation2 = operation2()
        val result = operation1 + operation2
        Log.i("testTwoLongOperations", "end")
        return result
    }

    suspend fun testTwoLongOperationsAsync(): Int = withContext(Dispatchers.Default) {
        Log.i("testTwoLongOperations", "start")
        val operation1 = async { operation1() }
        val operation2 = async { operation2() }

        val result = operation1.await() + operation2.await()
        Log.i("testTwoLongOperations", "end")
        
        return@withContext result
    }

    suspend fun operation1(): Int {
        Log.i("operation1", "start")
        delay(2000)
        Log.i("operation1", "end")
        return 2
    }

    suspend fun testApi() {
        try {
            val restaurantListSuspend = clienProvider.getRestaurantListSuspend()
        } catch (e: Exception) {
            TODO("Not yet implemented")
        }
    }

    suspend fun operation2(): Int {
        Log.i("operation2", "start")
        delay(3000)
        Log.i("operation2", "end")
        return 3
    }

    suspend fun testMutex() {
        mutex.withLock {
            Log.i("testMutex 1", "start")
            delay(5000)
            Log.i("testMutex 1", "end")
        }

        mutex.withLock {
            Log.i("testMutex 2", "start")
            delay(2000)
            Log.i("testMutex 2", "start")
        }
    }


    sealed class ComposeState {
        class ShowList(val listSize: Int) : ComposeState()
        class Loading() : ComposeState()
    }

}