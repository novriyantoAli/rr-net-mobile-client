package net.coblos.rrnet.domain.model.res

import com.google.gson.annotations.SerializedName
import net.coblos.rrnet.domain.model.ClientAuth

data class PostVerificationRes(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String?,
    @SerializedName("detailed")
    val detailed: Any?,
    @SerializedName("data")
    val data: ClientAuth?
)