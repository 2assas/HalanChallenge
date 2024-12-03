plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
kotlin{
    jvmToolchain(21)
}
tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.kotlin)
}
