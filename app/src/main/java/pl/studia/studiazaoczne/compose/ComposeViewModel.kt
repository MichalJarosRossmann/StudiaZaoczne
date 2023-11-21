package pl.studia.studiazaoczne.compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ComposeViewModel : ViewModel() {

    private val _listSizeLiveData = MutableLiveData<ComposeState>(ComposeState.ShowList(0))
    val listSizeLiveData: LiveData<ComposeState> = _listSizeLiveData

    fun changeListSize(listSize: Int) {

        CoroutineScope(Dispatchers.Main).launch {
            _listSizeLiveData.postValue(ComposeState.Loading())
            delay(1000)
            _listSizeLiveData.postValue(ComposeState.ShowList(listSize))
        }

    }


    sealed class ComposeState {
        class ShowList(val listSize: Int) : ComposeState()
        class Loading() : ComposeState()
    }

}