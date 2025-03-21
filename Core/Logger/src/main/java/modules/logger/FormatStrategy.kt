package modules.logger

interface FormatStrategy {
    fun log(priority: Int, tag: String?, message: String)
}