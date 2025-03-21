package modules.logger

import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException

/**
 * Provides convenient methods for some common operations.
 */
object Utils {
    /**
     * Returns true if a and b are equal, including if they are both null.
     *
     * @param a first CharSequence to check
     * @param b second CharSequence to check
     * @return true if a and b are equal
     */
    fun equals(a: CharSequence?, b: CharSequence?): Boolean {
        if (a === b) return true

        if (a != null && b != null) {
            val length = a.length
            if (length == b.length) {
                return if (a is String && b is String) {
                    a == b
                } else {
                    for (i in 0 until length) {
                        if (a[i] != b[i]) return false
                    }
                    true
                }
            }
        }
        return false
    }

    /**
     * Copied from "android.util.Log.getStackTraceString()" in order to avoid usage of Android stack
     * in unit tests.
     *
     * @return Stack trace in form of String
     */
    fun getStackTraceString(tr: Throwable?): String {
        if (tr == null) {
            return ""
        }

        // Reduce log spew for non-error conditions like network being unavailable.
        var t: Throwable? = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }

        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }

    fun toString(obj: Any?): String {
        return when {
            obj == null -> "null"
            !obj.javaClass.isArray -> obj.toString()
            obj is BooleanArray -> obj.contentToString()
            obj is ByteArray -> obj.contentToString()
            obj is CharArray -> obj.contentToString()
            obj is ShortArray -> obj.contentToString()
            obj is IntArray -> obj.contentToString()
            obj is LongArray -> obj.contentToString()
            obj is FloatArray -> obj.contentToString()
            obj is DoubleArray -> obj.contentToString()
            obj is Array<*> -> obj.contentDeepToString()
            else -> "Couldn't find a correct type for the object"
        }
    }
}