package modules.logger

import android.util.Log

class LogcatLogStrategy : LogStrategy {
    companion object {
        private const val DEFAULT_TAG = "NO_TAG"
    }

    override fun log(priority: Int, tag: String?, message: String) {
        Log.println(priority, tag ?: DEFAULT_TAG, message)
    }
}