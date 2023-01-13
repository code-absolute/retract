package tech.codeabsolute.retract

import kotlin.random.Random

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun String.Companion.random() = (1..30)
	.map { Random.nextInt(0, charPool.size) }
	.map(charPool::get)
	.joinToString("")

fun Int.Companion.random(from: Int = 0, to: Int = MAX_VALUE) = Random.nextInt(from, to)

fun Boolean.Companion.random() = Random.nextBoolean()