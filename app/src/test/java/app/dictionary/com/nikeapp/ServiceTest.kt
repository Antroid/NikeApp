package app.dictionary.com.nikeapp

import app.dictionary.com.nikeapp.data.DictionaryService
import app.dictionary.com.nikeapp.data.pojo.DictionaryResponse
import app.dictionary.com.nikeapp.util.DICTIONARY_BASE_URL
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceTest {

    @Test
    fun TestWebCallSuccess() {
        val mockWebServer = MockWebServer()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url(DICTIONARY_BASE_URL).toString())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Set a response for retrofit to handle. You can copy a sample
        //response from your server to simulate a correct result or an error.
        //MockResponse can also be customized with different parameters
        //to match your test needs
        mockWebServer.enqueue(MockResponse().setBody(getJsonSuccess()))

        val service = retrofit.create(DictionaryService::class.java)

        //With your service created you can now call its method that should
        //consume the MockResponse above. You can then use the desired
        //assertion to check if the result is as expected. For example:
        val call = service.getEntriesByTerm("test")


        val obsRes = TestObserver<DictionaryResponse>()
        call.subscribeWith(obsRes)


        obsRes
            .assertSubscribed()
            .assertComplete()
            .assertNoErrors()

        val dictionaryResponse = obsRes.values()[0]
        assert(dictionaryResponse.list.size==10)


        //Finish web server
        mockWebServer.shutdown()
    }

    private fun getJsonSuccess() : String{
        return "{\"list\":[{\"definition\":\"[wangda] [and the] [guys]\",\"permalink\":\"http://watg.urbanup.com/12255479\",\"thumbs_up\":0,\"sound_urls\":[],\"author\":\"joecool115\",\"word\":\"watg\",\"defid\":12255479,\"current_vote\":\"\",\"written_on\":\"2017-12-08T00:00:00.000Z\",\"example\":\"[Well well well], once again we have a watg [situation].\",\"thumbs_down\":0}]}"
    }


}