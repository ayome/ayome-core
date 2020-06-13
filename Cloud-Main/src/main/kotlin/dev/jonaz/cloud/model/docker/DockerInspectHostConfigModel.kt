package dev.jonaz.cloud.model.docker

import com.google.gson.annotations.SerializedName

data class DockerInspectHostConfigModel(
    @SerializedName("Binds")
    val binds: List<String>,
    @SerializedName("ContainerIDFile")
    val containerIdFile: String,
    @SerializedName("LogConfig")
    val logConfig: Any,
    @SerializedName("NetworkMode")
    val networkMode: String,
    @SerializedName("PortBindings")
    val portBindings: Map<*, *>,
    @SerializedName("RestartPolicy")
    val restartPolicy: Any,
    @SerializedName("AutoRemove")
    val autoRemove: Boolean,
    @SerializedName("VolumeDriver")
    val volumeDriver: String,
    @SerializedName("VolumesFrom")
    val VolumesFrom: Any?,
    @SerializedName("CapAdd")
    val capAdd: Any?,
    @SerializedName("CapDrop")
    val capDrop: Any?,
    @SerializedName("Capabilities")
    val capabilities: Any?,
    @SerializedName("Dns")
    val dns: List<Any>,
    @SerializedName("DnsOptions")
    val dnsOptions: List<Any>,
    @SerializedName("DnsSearch")
    val dnsSearch: List<Any>,
    @SerializedName("ExtraHosts")
    val extraHosts: Any?,
    @SerializedName("GroupAdd")
    val groupAdd: Any?,
    @SerializedName("GroupAdd")
    val ipcMode: String,
    @SerializedName("Cgroup")
    val cGroup: String,
    @SerializedName("Links")
    val links: Any?,
    @SerializedName("OomScoreAdj")
    val oomScoreAdj: Int,
    @SerializedName("PidMode")
    val pidMode: String,
    @SerializedName("Privileged")
    val privileged: Boolean,
    @SerializedName("PublishAllPorts")
    val publishAllPorts: Boolean,
    @SerializedName("ReadonlyRootfs")
    val readOnlyRootFs: Boolean,
    @SerializedName("SecurityOpt")
    val securityOpt: Any?,
    @SerializedName("UTSMode")
    val utsMode: String,
    @SerializedName("UsernsMode")
    val usernsMode: String,
    @SerializedName("ShmSize")
    val shmSize: Int,
    @SerializedName("Runtime")
    val runtime: String,
    @SerializedName("ConsoleSize")
    val consoleSize: List<Int>,
    @SerializedName("Isolation")
    val isolation: String,
    @SerializedName("CpuShares")
    val cpuShares: Int,
    @SerializedName("Memory")
    val memory: Int,
    @SerializedName("NanoCpus")
    val nanoCpus: Int,
    @SerializedName("CgroupParent")
    val cGroupParent: String,
    @SerializedName("BlkioWeight")
    val blkioWeight: Int,
    @SerializedName("BlkioWeightDevice")
    val blkioWeightDevice: List<Any>,
    @SerializedName("BlkioDeviceReadBps")
    val blkioDeviceReadBps: Any?,
    @SerializedName("BlkioDeviceWriteBps")
    val blkioDeviceWriteBps: Any?,
    @SerializedName("BlkioDeviceReadIOps")
    val blkioDeviceReadIOps: Any?,
    @SerializedName("BlkioDeviceWriteIOps")
    val blkioDeviceWriteIOps: Any?,
    @SerializedName("CpuPeriod")
    val cpuPeriod: Int,
    @SerializedName("CpuQuota")
    val cpuQuota: Int,
    @SerializedName("CpuRealtimePeriod")
    val cpuRealtimePeriod: Int,
    @SerializedName("CpuRealtimeRuntime")
    val cpuRealtimeRuntime: Int,
    @SerializedName("CpusetCpus")
    val cpuSetCpus: String,
    @SerializedName("CpusetMems")
    val cpuSetMems: String,
    @SerializedName("Devices")
    val devices: List<Any>,
    @SerializedName("DeviceCgroupRules")
    val deviceCgroupRules: Any?,
    @SerializedName("DeviceRequests")
    val deviceRequests: Any?,
    @SerializedName("KernelMemory")
    val kernelMemory: Int,
    @SerializedName("KernelMemoryTCP")
    val kernelMemoryTcp: Int,
    @SerializedName("MemoryReservation")
    val memoryReservation: Int,
    @SerializedName("memorySwap")
    val memorySwap: Int,
    @SerializedName("MemorySwappiness")
    val memorySwappiness: Boolean,
    @SerializedName("OomKillDisable")
    val oomKillDisable: Boolean,
    @SerializedName("PidsLimit")
    val pidsLimit: Any?,
    @SerializedName("Ulimits")
    val uLimits: Any?,
    @SerializedName("CpuCount")
    val cpuCount: Int,
    @SerializedName("cpuPercent")
    val cpuPercent: Int,
    @SerializedName("IOMaximumIOps")
    val ioMaximumIOps: Int,
    @SerializedName("IOMaximumBandwidth")
    val ioMaximumBandwidth: Int,
    @SerializedName("MaskedPaths")
    val maskedPaths: List<String>,
    @SerializedName("ReadonlyPaths")
    val readonlyPaths: List<String>
)
