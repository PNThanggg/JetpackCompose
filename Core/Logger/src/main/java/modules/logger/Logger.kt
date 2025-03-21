package modules.logger

/**
 * <pre>
 *  ┌────────────────────────────────────────────
 *  │ LOGGER
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Standard logging mechanism
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ But more pretty, simple and powerful
 *  └────────────────────────────────────────────
 * </pre>
 *
 * <h3>How to use it</h3>
 * Initialize it first
 * <pre><code>
 *   Logger.addLogAdapter(new AndroidLogAdapter());
 * </code></pre>
 *
 * And use the appropriate static Logger methods.
 *
 * <pre><code>
 *   Logger.d("debug");
 *   Logger.e("error");
 *   Logger.w("warning");
 *   Logger.v("verbose");
 *   Logger.i("information");
 *   Logger.wtf("What a Terrible Failure");
 * </code></pre>
 *
 * <h3>String format arguments are supported</h3>
 * <pre><code>
 *   Logger.d("hello %s", "world");
 * </code></pre>
 *
 * <h3>Collections are support ed(only available for debug logs)</h3>
 * <pre><code>
 *   Logger.d(MAP);
 *   Logger.d(SET);
 *   Logger.d(LIST);
 *   Logger.d(ARRAY);
 * </code></pre>
 *
 * <h3>Json and Xml support (output will be in debug level)</h3>
 * <pre><code>
 *   Logger.json(JSON_CONTENT);
 *   Logger.xml(XML_CONTENT);
 * </code></pre>
 *
 * <h3>Customize Logger</h3>
 * Based on your needs, you can change the following settings:
 * <ul>
 *   <li>Different {@link LogAdapter}</li>
 *   <li>Different {@link FormatStrategy}</li>
 *   <li>Different {@link LogStrategy}</li>
 * </ul>
 *
 * @see LogAdapter
 * @see FormatStrategy
 * @see LogStrategy
 */
object Logger {
    private var printer: Printer = LoggerPrinter()

    /**
     * Adds a log adapter.
     */
    fun addLogAdapter(adapter: LogAdapter) {
        printer.addAdapter(adapter)
    }

    /**
     * Clears all log adapters.
     */
    fun clearLogAdapters() {
        printer.clearLogAdapters()
    }

    /**
     * General log function that accepts all configurations as parameter.
     */
    fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        printer.log(priority, tag, message, throwable)
    }

    fun d(message: String, vararg args: Any?) {
        printer.d(message, *args)
    }

    fun d(obj: Any?) {
        printer.d(obj)
    }

    fun e(message: String, vararg args: Any?) {
        printer.e(null, message, *args)
    }

    fun e(throwable: Throwable?, message: String, vararg args: Any?) {
        printer.e(throwable, message, *args)
    }

    fun i(message: String, vararg args: Any?) {
        printer.i(message, *args)
    }

    fun v(message: String, vararg args: Any?) {
        printer.v(message, *args)
    }

    fun w(message: String, vararg args: Any?) {
        printer.w(message, *args)
    }

    /**
     * Tip: Use this for exceptional situations to log,
     * e.g., unexpected errors.
     */
    fun wtf(message: String, vararg args: Any?) {
        printer.wtf(message, *args)
    }

    /**
     * Formats the given JSON content and prints it.
     */
    fun json(json: String) {
        printer.json(json)
    }

    /**
     * Formats the given XML content and prints it.
     */
    fun xml(xml: String) {
        printer.xml(xml)
    }
}