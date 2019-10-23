package app.dictionary.com.nikeapp.data

import app.dictionary.com.nikeapp.data.pojo.DictionaryResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface DictionaryService {

    @GET("define")
    @Headers("x-rapidapi-key:b416cda1a8msha30c50d18edb177p1650aajsn464e1b024697",
             "x-rapidapi-host:mashape-community-urban-dictionary.p.rapidapi.com")
    fun getEntriesByTerm(@Query("term") term : String) : Single<DictionaryResponse>

}

