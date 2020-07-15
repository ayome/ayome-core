import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "2.0.4"
    kotlin("jvm") version "1.3.71"
}

group = "dev.jonaz.cloud.internal.addon"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    compileOnly("net.md-5:bungeecord-api:1.15-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.61")

    shadow("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.named<ShadowJar>("shadowJar") {
    manifest {
        attributes(mapOf("Main-Class" to "$group/Main"))
    }
}

tasks.build {
    dependsOn("shadowJar")
}
