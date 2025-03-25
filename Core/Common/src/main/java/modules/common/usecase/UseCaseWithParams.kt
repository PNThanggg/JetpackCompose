package modules.common.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import modules.common.di.ApplicationScope
import modules.common.di.IODispatcher
import modules.common.failure.Failure
import modules.common.functional.Either

/**
 * Abstract class for a Use Case (Interact in terms of
 * Clean Architecture naming convention).
 *
 * This abstraction represents an execution unit for
 * different use cases (this means that any use case
 * in the application should implement this contract).
 *
 * By convention each [UseCaseWithParams] implementation will
 * execute its job in a pool of threads using
 * [Dispatchers.IO].
 *
 * The result of the computation will be posted on the
 * same thread used by the @param 'scope' [CoroutineScope].
 */
abstract class UseCaseWithParams<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(
        @ApplicationScope scope: CoroutineScope,
        @IODispatcher dispatcher: CoroutineDispatcher,
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        scope.launch {
            val deferredJob = async(dispatcher) {
                run(params)
            }
            onResult(deferredJob.await())
        }
    }
}
