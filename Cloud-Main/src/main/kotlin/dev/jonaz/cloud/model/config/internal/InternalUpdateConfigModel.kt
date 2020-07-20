package dev.jonaz.cloud.model.config.internal

import com.google.gson.annotations.SerializedName

data class InternalUpdateConfigModel(
    @SerializedName("update")
    val update: Boolean,
    @SerializedName("sources")
    val sources: List<InternalUpdateSourceModel>
)
