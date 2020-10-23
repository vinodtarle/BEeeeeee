package com.beershop.adgaon.base.view

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.beershop.adgaon.R
import com.beershop.adgaon.base.utility.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ActivityMain : BaseActivity(layoutResourceId = R.layout.activity_main) {
    override fun className(): String = this.javaClass.simpleName

    private var optionMenu: Menu? = null

    // TODO : Option menu in action bar on main page
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        this.optionMenu = menu
        return true
    }

    // TODO : Handel option menu selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //val navigation = navigation as NavHostFragment
        return when (item.itemId) {
            R.id.optionMenuId -> {
                //navigation.navController.navigate(R.id.fragmentMain)
                true
            }
            android.R.id.home -> {
                hideKeyboard()
                super.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    // TODO : Show back arrow button on action bar when enable = true
    fun homeBackButton(visible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    // TODO :  Hide option menu from action bar when visible = false
    fun homeOptionMenu(visible: Boolean) {
        this.optionMenu?.let { menu ->
            menu.findItem(R.id.optionMenuId).isVisible = visible
        }
    }

    // TODO : User sign out from app
    private fun logout() {
        super.signOut()
    }
}
