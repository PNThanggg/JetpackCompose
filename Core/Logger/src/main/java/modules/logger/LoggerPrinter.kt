package modules.logger

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

class LoggerPrinter : Printer {
    companion object {
        /**
         * It is used for json pretty print
         */
        private const val JSON_INDENT = 2
    }

    /**
     * Provides one-time used tag for the log message
     */
    private val localTag = ThreadLocal<String>()

    private val logAdapters = mutableListOf<LogAdapter>()

    override fun addAdapter(adapter: LogAdapter) {
        logAdapters.add(adapter)
    }

    override fun d(message: String, vararg args: Any?) {
        log(Log.DEBUG, null, message, args)
    }

    override fun d(obj: Any?) {
        log(Log.DEBUG, null, Utils.toString(obj))
    }

    override fun e(message: String, vararg args: Any?) {
        e(null, message, args)
    }

    override fun e(throwable: Throwable?, message: String, vararg args: Any?) {
        log(Log.ERROR, throwable, message, args)
    }

    override fun w(message: String, vararg args: Any?) {
        log(Log.WARN, null, message, args)
    }

    override fun i(message: String, vararg args: Any?) {
        log(Log.INFO, null, message, args)
    }

    override fun v(message: String, vararg args: Any?) {
        log(Log.VERBOSE, null, message, args)
    }

    override fun wtf(message: String, vararg args: Any?) {
        log(Log.ASSERT, null, message, args)
    }

    override fun json(json: String) {
        try {
            val json = json.trim()
            when {
                json.startsWith("{") -> {
                    val jsonObject = JSONObject(json)
                    val message = jsonObject.toString(JSON_INDENT)
                    d(message)
                    return
                }

                json.startsWith("[") -> {
                    val jsonArray = JSONArray(json)
                    val message = jsonArray.toString(JSON_INDENT)
                    d(message)
                    return
                }

                else -> e("Invalid Json")
            }
        } catch (e: JSONException) {
            e(e.message ?: "Invalid Json")
        }
    }

    override fun xml(xml: String) {
        try {
            val xmlInput = StreamSource(StringReader(xml))
            val writer = StringWriter()
            val xmlOutput = StreamResult(writer)
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            transformer.transform(xmlInput, xmlOutput)
            d(writer.toString().replaceFirst(">", ">\n"))
        } catch (e: TransformerException) {
            e(e.message ?: "Invalid xml")
        }
    }

    override fun log(
        priority: Int, tag: String?, message: String, throwable: Throwable?
    ) {
        for (adapter in logAdapters) {
            if (adapter.isLoggable(priority, tag)) {
                if (throwable != null) {
                    adapter.log(priority, tag, "$message : ${Utils.getStackTraceString(throwable)}")
                } else if (message.isEmpty()) {
                    adapter.log(priority, tag, "Empty/NULL log message")
                } else {
                    adapter.log(priority, tag, message)
                }
            }
        }
    }

    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    private fun log(priority: Int, throwable: Throwable?, msg: String, vararg args: Any?) {
        val tag = getTag()
        val message = createMessage(msg, args)
        log(priority, tag, message, throwable)
    }

    private fun getTag(): String? {
        return localTag.get()?.also { localTag.remove() }
    }

    fun createMessage(message: String, vararg args: Any?): String {
        return if (args.isEmpty()) message else String.format(message, *args)
    }

    override fun clearLogAdapters() {
        logAdapters.clear()
    }
}