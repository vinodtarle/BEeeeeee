package com.beershop.adgaon.base.view

import android.os.Bundle
import android.view.View
import com.beershop.adgaon.R
import com.beershop.adgaon.base.utility.extension.homeBackButton
import com.beershop.adgaon.base.utility.extension.homeOptionMenu
import com.beershop.adgaon.base.utility.extension.setTitle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class FragmentMain internal constructor(private val someString: String = "") :
    BaseFragment(R.layout.fragment_main) {

    override fun className(): String = this.javaClass.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModel.setStateEvent(MainStateEvent.GetBlogEvent)

        init()
        initObserver()

        setTitle()
        homeBackButton(visible = false)
        homeOptionMenu(visible = true)
    }

    override fun init() {

    }
}