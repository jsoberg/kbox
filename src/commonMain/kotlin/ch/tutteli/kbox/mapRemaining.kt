package ch.tutteli.kbox

/**
 * Maps remaining values of this [Iterator] with the help of the given [transform] functions, returns an empty list if
 * [Iterator.hasNext] returns `false` for the first call.
 *
 * @return A [List] of the mapped remaining values.
 */
fun <T, R> Iterator<T>.mapRemaining(transform: (T) -> R): List<R> {
    val mutableList = mutableListOf<R>()
    forEachRemaining { mutableList.add(transform(it)) }
    return mutableList
}

/**
 * Maps remaining values of this [Iterator] with the help of the given [transform] functions where a counter variable
 * is passed ot the [transform] function starting by 0; returns an empty list if [Iterator.hasNext] returns `false`
 * for the first call.
 *
 * @return A [List] of the mapped remaining values.
 */
fun <T, R> Iterator<T>.mapRemainingWithCounter(transform: (index: Int, T) -> R): List<R> {
    val mutableList = mutableListOf<R>()
    var counter = 0
    forEachRemaining {
        mutableList.add(transform(counter, it))
        ++counter
    }
    return mutableList
}
