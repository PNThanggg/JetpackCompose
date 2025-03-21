package modules.logger

class AndroidLogAdapter(
    private val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder().build(),
) : LogAdapter {
    override fun isLoggable(priority: Int, tag: String?) = true

    override fun log(priority: Int, tag: String?, message: String) {
        formatStrategy.log(priority, tag, message)
    }
}