package app.dictionary.com.nikeapp.di.components

import android.app.Application
import app.dictionary.com.nikeapp.di.modules.ActivityBindingModule
import app.dictionary.com.nikeapp.di.modules.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,
    AndroidSupportInjectionModule::class, ActivityBindingModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }


}