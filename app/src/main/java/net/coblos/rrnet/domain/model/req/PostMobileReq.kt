package net.coblos.rrnet.domain.model.req

import com.google.gson.annotations.SerializedName

data class PostMobileReq(
    @SerializedName("mobile") val mobile: String
)