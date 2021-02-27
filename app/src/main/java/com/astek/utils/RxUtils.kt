package com.astek.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


fun Disposable.disposeBy(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}
