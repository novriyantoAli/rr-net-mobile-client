package net.coblos.rrnet.domain.model.res

import com.google.gson.annotations.SerializedName

data class PostMobileRes(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String?,
    @SerializedName("detailed")
    val detailed: Any?
)