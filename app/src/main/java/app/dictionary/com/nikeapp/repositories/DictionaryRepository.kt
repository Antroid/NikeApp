package app.dictionary.com.nikeapp.repositories

import app.dictionary.com.nikeapp.data.DictionaryService
import app.dictionary.com.nikeapp.data.local.EntryData
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DictionaryRepository @Inject constructor(private var dictionaryService: DictionaryService) {


    fun getEntriesByTerm(term : String) : Single<ArrayList<EntryData>>
    {
        return dictionaryService.getEntriesByTerm(term)
            .flatMap { res->Observable.just(res.list)
            .flatMapIterable { data->data }
            .map { item->EntryData(item.defid!!,item.definition!!,item.thumbsUp!!,item.thumbsDown!!) }
            .toList()
            .map { ArrayList(it) }
        }
    }

}
