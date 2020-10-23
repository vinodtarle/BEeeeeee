package com.beershop.adgaon.base.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.beershop.adgaon.R
import com.beershop.adgaon.base.utility.constant.Converter
import com.beershop.adgaon.base.utility.extension.hideKeyboard
import com.beershop.adgaon.base.utility.extension.homeBackButton
import com.beershop.adgaon.base.utility.extension.homeOptionMenu
import com.google.android.material.snackbar.Snackbar
import java.util.*

abstract class BaseFragment(layoutResourceId: Int) : Fragment(layoutResourceId) {

    abstract fun className(): String

    val TAG = className()

    private lateinit var layoutView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initListener()
        initObserver()
        initObservers()
        initNavigation()
        initRecyclerView()
        initData()

        homeBackButton()
        homeOptionMenu()
    }

    open fun init() {}

    open fun initListener() {}

    open fun initObserver() {}

    open fun initObservers() {}

    open fun initNavigation() {}

    open fun initRecyclerView() {}

    open fun initData() {}

    fun getAppName(): String = getString(R.string.app_name)

    fun hideKeyboard() = activity?.hideKeyboard()

    fun showProgressBar() {
        //layoutView.layoutProgressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        //layoutView.layoutProgressBar.visibility = View.VISIBLE
    }

    fun showFullScreenError() {

    }

    fun datePickerDialog(onClick: (date: String) -> Unit = {}) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(
            requireContext(), { _, cYear, cMonth, cDay ->
                onClick.invoke(Converter.toDMY(cDay, (cMonth + 1), cYear))
            }, year, month, day
        ).run { show() }
    }

    fun timePickerDialog(onClick: (time: String) -> Unit = {}) {
        val calendar = Calendar.getInstance()
        val mHour = calendar.get(Calendar.HOUR_OF_DAY)
        val mMinute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(
            requireContext(), { _, hourOfDay, minute ->
                onClick.invoke(Converter.toHM(hourOfDay, minute))
            }, mHour, mMinute, true
        ).run { show() }
    }

    fun showMessage(id: Int) {
        Snackbar(id).show()
    }

    private fun Snackbar(id: Int) =
        Snackbar.make(requireView(), id, Snackbar.LENGTH_LONG)

    fun showSuccessAdd() {
        Snackbar(R.string.success_add).show()
    }

    fun showErrorAdd() {
        Snackbar(R.string.error_add).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}