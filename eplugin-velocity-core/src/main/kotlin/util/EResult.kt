package top.e404.eplugin.util

@Suppress("UNUSED")
class EResult<T : Any> internal constructor(
    val result: T?,
    val reason: String?
) {
    companion object {
        fun <T : Any> ok(data: T) = EResult(data, null)
        fun <T : Any> fail(message: String) = EResult<T>(null, message)
    }

    val isSuccess get() = result != null

    inline fun onSuccess(block: (T) -> Unit) = apply {
        if (isSuccess) block(result!!)
    }

    inline fun onFail(block: (String) -> Unit) = apply {
        if (!isSuccess) block(reason!!)
    }
}