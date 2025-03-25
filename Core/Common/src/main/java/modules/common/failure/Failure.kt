package modules.common.failure

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure(open val message: String) {
    data class NetworkConnection(
        override val message: String = "No Internet Connection"
    ) : Failure(message)

    data class ServerError(
        override val message: String = "Server Error Occurred"
    ) : Failure(message)

    /** * Extend this class for feature-specific failures. */
    abstract class FeatureFailure(override val message: String) : Failure(message)
}
