package mr.adkhambek.mvvm.datasource.db


interface EntityMapper<T, R> {
    fun mapTR(from: T): R
    fun mapRT(from: R): T
}