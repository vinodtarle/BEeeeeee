package com.beershop.adgaon.base.utility

sealed class BaseStateEvent {
    object GetBlogEvent : BaseStateEvent()
    object None : BaseStateEvent()
}