package com.yzyfdf.library.model

import com.flyco.tablayout.listener.CustomTabEntity

class TabEntity(var title: String, var selectedIcon: Int, var unSelectedIcon: Int) : CustomTabEntity {

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getSelectedIconUrl(): String? {
        return null
    }

    override fun getUnSelectedIconUrl(): String? {
        return null
    }

    override fun setSelectedIconUrl(selectedIconUrl: String) {

    }

    override fun setUnSelectedIconUrl(unSelectedIconUrl: String) {

    }
}
