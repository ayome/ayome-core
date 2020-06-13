package dev.jonaz.cloud.model.docker

import com.google.gson.annotations.SerializedName

data class DockerInspectModel(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Created")
    val created: String,
    @SerializedName("Path")
    val path: String,
    @SerializedName("Args")
    val args: List<*>,
    @SerializedName("State")
    val state: Any,
    @SerializedName("Image")
    val image: String,
    @SerializedName("ResolvConfPath")
    val resolvConfPath: String,
    @SerializedName("HostnamePath")
    val hostnamePath: String,
    @SerializedName("HostsPath")
    val hostsPath: String,
    @SerializedName("LogPath")
    val logPath: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("RestartCounter")
    val RestartCounter: Int,
    @SerializedName("Driver")
    val driver: String,
    @SerializedName("Platform")
    val platform: String,
    @SerializedName("MountLabel")
    val mountLabel: String,
    @SerializedName("ProcessLabel")
    val processLabel: String,
    @SerializedName("AppArmorProfile")
    val appArmorProfile: String,
    @SerializedName("ExecIDs")
    val execIDs: Int?,
    @SerializedName("HostConfig")
    val hostConfig: Any,
    @SerializedName("GraphDriver")
    val graphDriver: Any,
    @SerializedName("Mounts")
    val mounts: Any,
    @SerializedName("Config")
    val config: Any,
    @SerializedName("NetworkSettings")
    val networkSettings: Any
)
