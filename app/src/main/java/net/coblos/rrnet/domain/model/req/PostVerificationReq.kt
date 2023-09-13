package net.coblos.rrnet.domain.model.req

import com.google.gson.annotations.SerializedName

data class PostVerificationReq(
    @SerializedName("mobile") val mobile: String,
    @SerializedName("verification") val verification: String
)