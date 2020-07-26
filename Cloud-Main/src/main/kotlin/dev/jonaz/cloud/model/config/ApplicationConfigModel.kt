package dev.jonaz.cloud.model.config

import com.google.gson.annotations.SerializedName

data class ApplicationConfigModel(
    @SerializedName("ui-bind")
    val uiBind: String
)
