package com.astek.di

import com.astek.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelMapperModule::class,
        ViewBuilderModule::class,
        ListingBindModule::class,
        NetworkModule::class
    ]
)
interface ApplicationComponent {

    val androidInjector: DispatchingAndroidInjector<Any>

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun moviesApp(app: App): Builder

        fun build(): ApplicationComponent
    }
}

object MoviesApp {

    private lateinit var applicationComponent: ApplicationComponent

    @JvmStatic
    internal fun init(app: App) {
        applicationComponent = DaggerApplicationComponent.builder()
            .moviesApp(app)
            .build()
        inject(app)
    }

    internal fun inject(resource: Any) {
        applicationComponent.androidInjector.inject(resource)
    }

}
