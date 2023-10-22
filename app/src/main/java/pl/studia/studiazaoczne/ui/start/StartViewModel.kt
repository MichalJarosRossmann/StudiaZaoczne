package pl.studia.studiazaoczne.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartViewModel :ViewModel() {

    private val _textLiveData=MutableLiveData<ViewState>()
    val textLiveData:LiveData<ViewState> = _textLiveData


    fun setText(s: String) {
        viewModelScope.launch {
            _textLiveData.postValue(ViewState.Loading())
            delay(1000)
            // coś się dzieje
            _textLiveData.postValue(ViewState.ShowText("$s  super tekst"))
        }

    }

    sealed class ViewState{
        class ShowText(val text:String):ViewState()
        class Error():ViewState()
        class Loading():ViewState()
    }


}