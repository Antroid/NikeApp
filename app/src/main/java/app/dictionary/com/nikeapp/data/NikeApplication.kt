package app.dictionary.com.nikeapp.data

import app.dictionary.com.nikeapp.di.components.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class NikeApplication : DaggerApplication()
{
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().application(this).build()
    }
}