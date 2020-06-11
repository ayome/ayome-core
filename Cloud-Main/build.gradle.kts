plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.71"
    kotlin("plugin.spring") version "1.3.71"
}

group = "dev.jonaz.cloud"

setBuildDir("../build")

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.security:spring-security-core")

    implementation("org.springframework.shell:spring-shell-core:2.0.1.RELEASE")
    implementation("org.springframework.shell:spring-shell-standard:2.0.1.RELEASE")
    implementation("org.springframework.shell:spring-shell-shell1-adapter:2.0.1.RELEASE")
    implementation("org.springframework.shell:spring-shell-jcommander-adapter:2.0.1.RELEASE")
    implementation("org.springframework.shell:spring-shell-table:2.0.1.RELEASE")

    implementation("org.xerial:sqlite-jdbc:3.30.1")
    implementation("org.reflections:reflections:0.9.9")
    implementation("com.corundumstudio.socketio:netty-socketio:1.7.12")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    implementation("org.jetbrains.exposed:exposed-core:0.22.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.22.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.22.1")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    register("buildAll") {
        group = "application"

        dependsOn(":Cloud-UI:copyStaticFiles")
        finalizedBy("build")
    }
}
