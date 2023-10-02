package net.coblos.rrnet.domain.model

import com.google.gson.annotations.SerializedName

data class ClientAuth(
    @SerializedName("id")
    val id: String,

    @SerializedName("mobile")
    val mobile: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("token")
    val token: String
)