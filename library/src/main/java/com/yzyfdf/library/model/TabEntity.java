package com.yzyfdf.library.model;

import com.flyco.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {
    public String title;
    public int    selectedIcon;
    public int    unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }

    @Override
    public String getSelectedIconUrl() {
        return null;
    }

    @Override
    public String getUnSelectedIconUrl() {
        return null;
    }

    @Override
    public void setSelectedIconUrl(String selectedIconUrl) {

    }

    @Override
    public void setUnSelectedIconUrl(String unSelectedIconUrl) {

    }
}
