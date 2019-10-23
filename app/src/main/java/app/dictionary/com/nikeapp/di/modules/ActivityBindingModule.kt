package app.dictionary.com.nikeapp.di.modules

import app.dictionary.com.nikeapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector()
    internal abstract fun bindMainActivity(): MainActivity

}