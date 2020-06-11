plugins {
    id("com.moowork.node") version "1.3.1"
}

tasks.register<Copy>("copyStaticFiles") {
    dependsOn("deleteOldStaticFiles", "npm_run_build")

    from("$projectDir/dist/cloud-ui")
    into("$rootDir/Cloud-Main/src/main/resources/static")
}

tasks.register<Delete>("deleteOldStaticFiles") {
    delete("$rootDir/Cloud-Main/src/main/resources/static")
}
