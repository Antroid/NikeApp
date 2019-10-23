package app.dictionary.com.nikeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.dictionary.com.nikeapp.data.local.EntryData
import app.dictionary.com.nikeapp.repositories.DictionaryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class DictionaryViewModel @Inject
constructor() : ViewModel() {

    private var disposable: CompositeDisposable? = null
    private var dictionaryEntries = MutableLiveData<ArrayList<EntryData>>()
    private var isLoading = MutableLiveData<Boolean>()
    private var isError = MutableLiveData<Boolean>()

    @Inject
    lateinit var dictionaryRepository: DictionaryRepository

    init {
        disposable = CompositeDisposable()
    }

    fun getDictionaryEntries() : LiveData<ArrayList<EntryData>>
    {
        return dictionaryEntries
    }

    fun isError() : LiveData<Boolean>
    {
        return isError
    }

    fun isLoading() : LiveData<Boolean>
    {
        return isLoading
    }

    fun getDictionaryTerm(term : String)
    {
        disposable!!.add(dictionaryRepository.getEntriesByTerm(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(DictionaryChannelsSingleDisposableObserver()))
    }


    override fun onCleared() {
        super.onCleared()
        if(disposable!=null)
        {
            disposable!!.clear()
        }
        disposable = null
    }

    private inner class DictionaryChannelsSingleDisposableObserver : DisposableSingleObserver<ArrayList<EntryData>>(){
        override fun onStart() {
            isLoading.value = true
            isError.value = false
        }
        override fun onSuccess(t: ArrayList<EntryData>) {
            isError.value = false
            isLoading.value = false
            dictionaryEntries.value = t
        }

        override fun onError(e: Throwable) {
            isError.value = true
            isLoading.value = false
        }

    }

}