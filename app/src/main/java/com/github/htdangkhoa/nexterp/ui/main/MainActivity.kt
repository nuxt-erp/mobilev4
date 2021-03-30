package com.github.htdangkhoa.nexterp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.github.htdangkhoa.nexterp.R
import com.github.htdangkhoa.nexterp.base.BaseActivity
import com.github.htdangkhoa.nexterp.data.remote.stockcount.stockcount.StockCountResponse
import com.github.htdangkhoa.nexterp.resource.ObserverResource
import com.github.htdangkhoa.nexterp.ui.settings.SettingsActivity
import com.google.android.material.navigation.NavigationView
import com.pawegio.kandroid.startActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var toolbar: Toolbar

    override val layoutResID: Int
        get() = R.layout.activity_main

    /* This section is all related to setting up the navigation menu */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        nav_view.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    /* Populating the settings menu will all items from the 'menu_home_drawer' XML file */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    /* For opening the menu */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.action_settings) {

            startActivity<SettingsActivity>()
            return true
        } else if(id == R.id.action_logout) {
            viewModel.logout()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun render(savedInstanceState: Bundle?) {

        viewModel.resourceLogout.observe(this, object : ObserverResource<Array<String?>>() {
            override fun onSuccess(data: Array<String?>) {
                logout(401)
            }

            override fun onError(throwable: Throwable?) {
                handleError(throwable) {
                    it?.message?.let {
                        showDialog("Error", it)
                    }
                }
            }
        })
    }
}
