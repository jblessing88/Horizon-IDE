import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
}

group = "me.tonystark"
version = "1.0-SNAPSHOT"

val koin_version = "2.2.2"
val controlsfx_version = "11.0.3"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("javax.inject:javax.inject:1")
    // Koin for Kotlin
    implementation("org.koin:koin-core:$koin_version")
    // Koin extended & experimental features
    implementation("org.koin:koin-core-ext:$koin_version")
    implementation("org.controlsfx:controlsfx:$controlsfx_version")

    testImplementation("org.koin:koin-test:$koin_version")
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}