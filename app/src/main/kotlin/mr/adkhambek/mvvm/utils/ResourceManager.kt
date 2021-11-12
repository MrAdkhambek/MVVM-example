package mr.adkhambek.domain.util.resource

import android.graphics.drawable.Drawable

interface ResourceManager {

    fun getString(id: Int): String
    fun getStringArray(id: Int): Array<String>
    fun getString(id: Int, vararg formatArgs: Any): String

    fun getColor(id: Int): Int
    fun getResId(path: String): Int
    fun getIntArray(id: Int): IntArray

    fun getDrawable(id: Int): Drawable?
    fun getDrawableId(name: String): Int
}