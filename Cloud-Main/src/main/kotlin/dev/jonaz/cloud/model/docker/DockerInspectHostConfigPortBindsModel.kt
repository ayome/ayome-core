package dev.jonaz.cloud.model.docker

import com.google.gson.annotations.SerializedName

data class DockerInspectHostConfigPortBindsModel(
    @SerializedName("HostIp")
    val hostIp: String,
    @SerializedName("HostPort")
    val hostPort: Int
)
