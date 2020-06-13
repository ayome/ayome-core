package dev.jonaz.cloud.model.docker

import com.google.gson.annotations.SerializedName

data class DockerInspectMountsModel(
    @SerializedName("Type") val type: String,
    @SerializedName("Source") val source: String,
    @SerializedName("Destination") val destination: String,
    @SerializedName("Mode") val mode: String,
    @SerializedName("RW") val rw: Boolean,
    @SerializedName("Propagation") val propagation: String
)
