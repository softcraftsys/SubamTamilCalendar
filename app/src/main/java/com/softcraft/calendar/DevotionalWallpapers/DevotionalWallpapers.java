
package com.softcraft.calendar.DevotionalWallpapers;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DevotionalWallpapers {

    @SerializedName("wallpaperdata")
    @Expose
    private List<Wallpaperdatum> wallpaperdata = null;

    public List<Wallpaperdatum> getWallpaperdata() {
        return wallpaperdata;
    }

    public void setWallpaperdata(List<Wallpaperdatum> wallpaperdata) {
        this.wallpaperdata = wallpaperdata;
    }

}
