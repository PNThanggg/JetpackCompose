package jc.apps.clean_architecture.utils

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {

    /**
     * Represents the left side of [Either] class which by convention
     * is a "com.module.core.failure.Failure".
     */
    data class Left<out L>(val left: L) : Either<L, Nothing>()

    /**
     * Represents the right side of [Either] class which by convention
     * is a "Success".
     */
    data class Right<out R>(val right: R) : Either<Nothing, R>()

    /**
     * Returns true if this is a Right, false otherwise.
     * @see Right
     */
    val isRight get() = this is Right<R>

    /**
     * Returns true if this is a Left, false otherwise.
     * @see Left
     */
    val isLeft get() = this is Left<L>

    /**
     * Applies fnL if this is a Left or fnR if this is a Right.
     * @see Left
     * @see Right
     */
    fun <T> fold(fnL: (L) -> T, fnR: (R) -> T): T =
        when (this) {
            is Left -> fnL(left)
            is Right -> fnR(right)
        }

    /**
     * Applies fnL if this is a Left or fnR if this is a Right.
     *
     * Kotlin Coroutines Support.
     * @see Left
     * @see Right
     */
    suspend fun <T> coFold(fnL: suspend (L) -> T, fnR: suspend (R) -> T): T =
        when (this) {
            is Left -> fnL(left)
            is Right -> fnR(right)
        }

    companion object {
        /**
         * Transforms a try/catch in an com.module.core.functional.Either<Exception, Right>
         * See [mapException]
         * **/
        @Suppress("TooGenericExceptionCaught")
        suspend fun <Right> catch(
            operation: suspend () -> Right
        ): Either<Exception, Right> =
            try {
                operation().toRight()
            } catch (e: Exception) {
                e.toLeft()
            }
    }
}

/** If Left is of type Exception, allows to com.module.core.functional.map it to another type.
 * Rethrow the exception if [operation] returns null **/
fun <Left : Any, Right> Either<Exception, Right>.mapException(
    operation: (Exception) -> Left?
): Either<Left, Right> = when (this) {
    is Either.Left -> operation(left)?.toLeft() ?: throw left
    is Either.Right -> this
}

/** Represents the right side of [Either] class which by convention is a "Success". **/
fun <T> T.toRight(): Either.Right<T> =
    Either.Right(this)

/** Represents the left side of [Either] class which by convention is a "com.module.core.failure.Failure". */
fun <T> T.toLeft(): Either.Left<T> =
    Either.Left(this)

/**
 * Composes 2 functions
 * See <a href="https://proandroiddev.com/kotlins-nothing-type-946de7d464fb">Credits to Alex Hart.</a>
 */
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

/**
 * Right-biased com.module.core.functional.flatMap() FP convention which means that Right is assumed to be the default case
 * to operate on. If it is Left, operations like com.module.core.functional.map, com.module.core.functional.flatMap, ... return the Left value unchanged.
 */
fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Left -> Either.Left(left)
        is Either.Right -> fn(right)
    }

/**
 * Right-biased com.module.core.functional.flatMap() FP convention which means that Right is assumed to be the default case
 * to operate on. If it is Left, operations like com.module.core.functional.map, com.module.core.functional.flatMap, ... return the Left value unchanged.
 * It works with Kotlin Coroutines (suspension functions).
 */
suspend fun <T, L, R> Either<L, R>.coFlatMap(fn: suspend (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Left -> Either.Left(left)
        is Either.Right -> fn(right)
    }

/**
 * Left-biased com.module.core.functional.onFailure() FP convention dictates that when this class is Left, it'll perform
 * the com.module.core.functional.onFailure functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
infix fun <L, R> Either<L, R>.onFailure(fn: (failure: L) -> Unit): Either<L, R> =
    this.apply { if (this is Either.Left) fn(left) }

/**
 * Right-biased com.module.core.functional.onSuccess() FP convention dictates that when this class is Right, it'll perform
 * the com.module.core.functional.onSuccess functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
infix fun <L, R> Either<L, R>.onSuccess(fn: (success: R) -> Unit): Either<L, R> =
    this.apply { if (this is Either.Right) fn(right) }

/**
 * Right-biased com.module.core.functional.map() FP convention which means that Right is assumed to be the default case
 * to operate on. If it is Left, operations like com.module.core.functional.map, com.module.core.functional.flatMap, ... return the Left value unchanged.
 */
fun <L, R, T> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> =
    when (this) {
        is Either.Left -> Either.Left(left)
        is Either.Right -> Either.Right(fn(right))
    }

/**
 * Right-biased com.module.core.functional.map() FP convention which means that Right is assumed to be the default case
 * to operate on. If it is Left, operations like com.module.core.functional.map, com.module.core.functional.flatMap, ... return the Left value unchanged.
 * It works with Kotlin Coroutines (suspension functions).
 */
suspend fun <L, R, T> Either<L, R>.coMap(fn: suspend (R) -> (T)): Either<L, T> =
    when (this) {
        is Either.Left -> Either.Left(left)
        is Either.Right -> Either.Right(fn(right))
    }

/**
 * Returns the value from this `Right` or the given argument if this is a `Left`.
 * Right(12).com.module.core.functional.getOrElse(17) RETURNS 12 and Left(12).com.module.core.functional.getOrElse(17) RETURNS 17
 */
fun <L, R> Either<L, R>.getOrElse(value: R): R =
    when (this) {
        is Either.Left -> value
        is Either.Right -> right
    }
