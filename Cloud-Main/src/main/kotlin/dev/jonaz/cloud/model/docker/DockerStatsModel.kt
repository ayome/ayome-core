package dev.jonaz.cloud.model.docker

import com.google.gson.annotations.SerializedName

data class DockerStatsModel(
    @SerializedName("BlockIO")
    val blockIO: String,
    @SerializedName("CPUPerc")
    val cpuPerc: String,
    @SerializedName("Container")
    val container: String,
    @SerializedName("ID")
    val id: String,
    @SerializedName("MemPerc")
    val memPerc: String,
    @SerializedName("MemUsage")
    val memUsage: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("NetIO")
    val netIO: String,
    @SerializedName("PIDs")
    val pids: String
)
