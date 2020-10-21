package mr.adkhambek.mvvm.model


@DslMarker
annotation class ResourceDSL


@ResourceDSL
sealed class ResourceUI<out T> {
    object Loading : ResourceUI<Nothing>()
    data class Resource<T>(val data: T, val statusCode: Int = 200) : ResourceUI<T>()
    data class Error(val error: Throwable) : ResourceUI<Nothing>()
}


@ResourceDSL
inline fun <T> onResource(resource: ResourceUI<T>, body: (ResourceUI.Resource<T>) -> Unit) {
    if (resource is ResourceUI.Resource) body(resource)
}