package io.sireto.spring.common.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.createLogger(): Logger =
        LoggerFactory.getLogger(
                if (T::class.isCompanion) T::class.java.enclosingClass else T::class.java
        )
