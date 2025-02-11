package com.ilyeong.movieverse.data.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SessionInterceptor