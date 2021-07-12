package com.farkhodkhaknazarov.configurationupdater.api

enum class TemInvokerResultEnum(val result: Int) {
    UNKNOWN(999),
    ERROR(-1),
    COMPLETE(1),
    SUCCESS(0)
}