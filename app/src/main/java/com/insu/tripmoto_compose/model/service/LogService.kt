package com.insu.tripmoto_compose.model.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}