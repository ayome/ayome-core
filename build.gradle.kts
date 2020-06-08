plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE" // 2.3.0.RELEASE
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.71"
    kotlin("plugin.spring") version "1.3.71"
}

group = "dev.jonaz.cloud"
version = "0.1-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.security:spring-security-core")
    implementation("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.shell:spring-shell-core:2.0.1.RELEASE")
    implementation("org.springframework.shell:spring-shell-standard:2.0.1.RELEASE")
    implementation("org.springframework.shell:spring-shell-shell1-adapter:2.0.1.RELEASE")

    implementation("org.reflections:reflections:0.9.9")
    implementation("com.corundumstudio.socketio:netty-socketio:1.7.12")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
