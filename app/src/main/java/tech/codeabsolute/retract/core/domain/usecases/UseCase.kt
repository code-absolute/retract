package tech.codeabsolute.retract.core.domain.usecases

import kotlinx.coroutines.flow.Flow

interface UseCase<Input, Output> {
	operator fun invoke(input: Input): Flow<Output>
}

interface UseCaseWithNoInput<Output> {
	operator fun invoke(): Flow<Output>
}