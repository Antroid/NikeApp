package app.dictionary.com.nikeapp.di.modules

import app.dictionary.com.nikeapp.data.DictionaryService
import app.dictionary.com.nikeapp.util.DICTIONARY_BASE_URL
import app.globe.com.youtubeplaylist.di.modules.ViewModelModule
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ApplicationModule {


    @Singleton
    @Provides
    internal fun provideRetrofit(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }

        val client = OkHttpClient.Builder()
//            .addInterceptor(object : Interceptor {
//                @Throws(IOException::class)
//                override fun intercept(chain: Interceptor.Chain): Response {
//                    val request = chain.request().newBuilder()
//                        .addHeader("x-rapidapi-host", "mashape-community-urban-dictionary.p.rapidapi.com")
//                        .addHeader("x-rapidapi-key", "b416cda1a8msha30c50d18edb177p1650aajsn464e1b024697")
//                        .build()
//                    return chain.proceed(request)
//                }
//            })
            .addInterceptor(interceptor)
            .build()


        return Retrofit.Builder().baseUrl(DICTIONARY_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


    @Singleton
    @Provides
    internal fun provideDictionaryService(retrofit: Retrofit): DictionaryService {
        return retrofit.create(DictionaryService::class.java)
    }

}