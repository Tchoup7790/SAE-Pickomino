import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
    id("org.openjfx.javafxplugin") version "0.0.13"
    application
}

group = "iut.info1"
version = "1.0-SNAPSHOT"
val ktor_version: String by project

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
    implementation("org.slf4j:slf4j-nop:2.0.7")
    implementation("org.controlsfx:controlsfx:11.1.2")
    implementation(files("lib/pickomino-lib-1.5.jar"))
    implementation(files("lib/jimColModelImporterJFX.jar"))
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
}

javafx {
    version = "19"
    modules("javafx.controls", "javafx.fxml", "javafx.media")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}
