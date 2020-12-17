package com.github.htdangkhoa.cleanarchitecture.di

import com.github.htdangkhoa.cleanarchitecture.di.module.NetModule
import com.github.htdangkhoa.cleanarchitecture.ui.login.LoginModule
import com.github.htdangkhoa.cleanarchitecture.ui.main.MainModule
import com.github.htdangkhoa.cleanarchitecture.ui.receiving.ReceivingModule
import com.github.htdangkhoa.cleanarchitecture.ui.settings.SettingsModule
import com.github.htdangkhoa.cleanarchitecture.ui.splash.SplashModule
import com.github.htdangkhoa.cleanarchitecture.ui.stockcount.StockCountModule

object AppComponent {
    val components = listOf(
        NetModule.module,
        SplashModule.module,
        LoginModule.module,
        MainModule.module,
        SettingsModule.module,
        ReceivingModule.module,
        StockCountModule.module,
    )
}
