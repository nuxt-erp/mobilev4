package com.github.htdangkhoa.nexterp.di

import com.github.htdangkhoa.nexterp.di.module.NetModule
import com.github.htdangkhoa.nexterp.ui.login.LoginModule
import com.github.htdangkhoa.nexterp.ui.main.MainModule
import com.github.htdangkhoa.nexterp.ui.main.fragments.availability.AvailabilityModule
import com.github.htdangkhoa.nexterp.ui.main.fragments.receiving.ReceivingModule
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockadjustment.StockAdjustmentModule
import com.github.htdangkhoa.nexterp.ui.main.fragments.stockcount.StockCountModule
import com.github.htdangkhoa.nexterp.ui.settings.SettingsModule
import com.github.htdangkhoa.nexterp.ui.splash.SplashModule

object AppComponent {
    val components = listOf(
        NetModule.module,
        SplashModule.module,
        LoginModule.module,
        MainModule.module,
        SettingsModule.module,
        ReceivingModule.module,
        StockCountModule.module,
        StockAdjustmentModule.module,
        AvailabilityModule.module,
    )
}
