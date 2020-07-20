package dev.jonaz.cloud.model.config.internal

import com.google.gson.annotations.SerializedName

data class InternalUpdateSourceModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("tag")
    var tag: String,
    @SerializedName("release")
    val release: String
)
