package com.astek.utils

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.TimeUnit

class TestSchedulerRule : TestRule {

    val testScheduler = TestScheduler()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { testScheduler }
                RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
                RxJavaPlugins.setNewThreadSchedulerHandler { testScheduler }
                RxAndroidPlugins.setMainThreadSchedulerHandler { testScheduler }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}

