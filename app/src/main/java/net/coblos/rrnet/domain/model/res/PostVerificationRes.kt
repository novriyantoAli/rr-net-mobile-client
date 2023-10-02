package net.coblos.rrnet.domain.model.res

import com.google.gson.annotations.SerializedName
import net.coblos.rrnet.domain.model.ClientAuth

data class PostVerificationRes(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message:String,

    @SerializedName("data")
    val data: ClientAuth
)