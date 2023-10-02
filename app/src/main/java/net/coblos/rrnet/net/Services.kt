package net.coblos.rrnet.net

import net.coblos.rrnet.domain.model.req.LoginRequest
import net.coblos.rrnet.domain.model.req.PostVerificationReq
import net.coblos.rrnet.domain.model.res.PostMobileRes
import net.coblos.rrnet.domain.model.res.PostVerificationRes
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

interface Services {
    @FormUrlEncoded
    @POST
    suspend fun login(@Url url: String, @Field("username") uname: String, @Field("password") psswd: String): String

    @POST
    suspend fun postMobile(@Url url: String, @Body mobile: String): PostMobileRes

    @POST
    suspend fun postVerification(@Url url: String, @Body verification: PostVerificationReq): PostVerificationRes
}