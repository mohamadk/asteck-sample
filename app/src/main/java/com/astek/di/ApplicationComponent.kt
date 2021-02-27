package com.astek.di

import dagger.Component
import dagger.android.AndroidInjectionModule

@Component(
    modules = [AndroidInjectionModule::class]
)
interface ApplicationComponent {
}
