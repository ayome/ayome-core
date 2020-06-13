package dev.jonaz.cloud.model.docker

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class DockerInspectStateModel(
    @SerializedName("Status")
    val status: String,
    @SerializedName("Running")
    val running: Boolean,
    @SerializedName("Paused")
    val paused: Boolean,
    @SerializedName("Restarting")
    val restarting: Boolean,
    @SerializedName("OOMKilled")
    val oomKilled: Boolean,
    @SerializedName("Dead")
    val dead: Boolean,
    @SerializedName("Pid")
    val pid: Int,
    @SerializedName("ExitCode")
    val exitCode: Int,
    @SerializedName("Error")
    val error: String,
    @SerializedName("StartedAt")
    val startedAt: LocalDateTime,
    @SerializedName("FinishedAt")
    val finishedAt: LocalDateTime
)
