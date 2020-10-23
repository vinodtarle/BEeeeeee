package com.beershop.adgaon.base.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.beershop.adgaon.blog.view.FragmentBlog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class BaseFragmentFactory @Inject internal constructor(
    private val someString: String
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            FragmentBlog::class.java.name -> FragmentBlog(someString)

            else -> super.instantiate(classLoader, className)
        }
    }
}