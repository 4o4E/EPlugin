package top.e404.eplugin.util

import kotlin.time.Duration

@Suppress("UNUSED")
val Duration.cnString get() = toComponents { d, h, m, s, nanoseconds ->
    buildString {
        if (d > 0) append("${d}天")
        if (h > 0) append("${h}时")
        if (m > 0) append("${m}分")
        if (s > 0) append("${s}秒")
    }
}