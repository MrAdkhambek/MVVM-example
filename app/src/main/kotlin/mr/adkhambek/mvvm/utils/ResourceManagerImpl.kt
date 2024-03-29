package mr.adkhambek.mvvm.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import mr.adkhambek.domain.util.resource.ResourceManager
import mr.adkhambek.mvvm.BuildConfig.APPLICATION_ID
import javax.inject.Inject


class ResourceManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ResourceManager {

    override fun getResId(path: String): Int {
        val parts: List<String> = path.split("/")
        if (parts.size == 3) {
            var part1 = parts[1]
            var part2 = parts[2]
            if (part1.contains("-")) part1 = part1.substring(0, part1.indexOf("-"))
            if (part2.contains(".")) part2 = part2.substring(0, part2.indexOf("."))
            return context.resources.getIdentifier(part2, part1, APPLICATION_ID)
        }
        return 0
    }

    override fun getString(id: Int): String = context.getString(id)
    override fun getString(id: Int, vararg formatArgs: Any): String = context.getString(id, *formatArgs)

    override fun getColor(id: Int): Int = ContextCompat.getColor(context, id)
    override fun getIntArray(id: Int): IntArray = context.resources.getIntArray(id)
    override fun getDrawable(id: Int): Drawable? = ContextCompat.getDrawable(context, id)
    override fun getStringArray(id: Int): Array<String> = context.resources.getStringArray(id)

    override fun getDrawableId(name: String): Int = context.resources.getIdentifier(name, "drawable", APPLICATION_ID)
}
