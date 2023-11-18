package pl.studia.studiazaoczne.compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ComposeViewModel : ViewModel() {

    private val _listSizeLiveData = MutableLiveData(0)
    val listSizeLiveData: LiveData<Int> = _listSizeLiveData

    fun changeListSize(listSize: Int) {
        _listSizeLiveData.postValue(listSize)
    }


}