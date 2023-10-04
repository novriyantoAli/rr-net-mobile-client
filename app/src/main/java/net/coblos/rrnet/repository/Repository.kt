package net.coblos.rrnet.repository

import kotlinx.coroutines.flow.Flow
import net.coblos.rrnet.domain.DataState
import net.coblos.rrnet.domain.model.res.PostMobileRes
import net.coblos.rrnet.domain.model.res.PostVerificationRes

interface Repository {
    suspend fun postLogin(url: String, username: String, password: String): Flow<DataState<String>>

    suspend fun postMobile(url: String, mobile: String): Flow<DataState<PostMobileRes>>

    suspend fun postVerification(url: String, verification: String): Flow<DataState<PostVerificationRes>>
}