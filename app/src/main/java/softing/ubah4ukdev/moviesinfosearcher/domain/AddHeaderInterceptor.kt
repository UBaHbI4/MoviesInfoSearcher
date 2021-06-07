package softing.ubah4ukdev.moviesinfosearcher.domain

import okhttp3.Interceptor
import okhttp3.Response

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.domain

Created by Ivan Sheynmaer

2021.06.08
v1.0
 */
class AddHeaderInterceptor : Interceptor {
    companion object {
        private const val HEADER_NAME = "Authorization"
        private const val HEADER_VALUE = "Secret token"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(HEADER_NAME, HEADER_VALUE)
            .build()
        return chain.proceed(request)
    }
}