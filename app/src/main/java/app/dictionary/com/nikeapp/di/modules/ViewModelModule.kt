package app.globe.com.youtubeplaylist.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.dictionary.com.nikeapp.di.scope.ViewModelKey
import app.dictionary.com.nikeapp.viewmodels.DictionaryViewModel
import app.dictionary.com.nikeapp.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DictionaryViewModel::class)
    abstract fun bindSettingsViewModel(discoverViewModel: DictionaryViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}