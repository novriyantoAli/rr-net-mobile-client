package net.coblos.rrnet.domain.model.req

import com.google.gson.annotations.SerializedName

data class PostVerificationReq(
    @SerializedName("verification")
    val verification: String
)