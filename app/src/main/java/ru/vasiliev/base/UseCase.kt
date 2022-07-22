package ru.vasiliev.base

abstract class UseCase<in A, out T> {

    abstract suspend fun execute(param: A): T
}