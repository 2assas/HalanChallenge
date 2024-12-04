plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.gradle.plugin)
}

android {
    namespace = "halan.challenge.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        // Accessing properties from gradle.properties
        buildConfigField("String", "BASE_URL", "\"https://api.twitter.com/2/\"")
        buildConfigField(
            "String",
            "CONSUMER_KEY",
            "\"ZZ2sU4lgEoYb8zUfPtBk4587y\""
        )
        buildConfigField(
            "String",
            "CONSUMER_SECRET",
            "\"3kz2bJQgvEa2cqG0F8caSsVTcdPEjXTln4hmby6pXYgcR1GSiX\""
        )
        buildConfigField(
            "String",
            "ACCESS_TOKEN",
            "\"1518010215727349762-O42xmzumC0ANLPRWOLjdiT4LNI1n3t\""
        )
        buildConfigField(
            "String",
            "TOKEN_SECRET",
            "\"a6UmoDLBoVQTXncYWLPVYDt0BZ5V2AX5uwIpJuTKNwzj2\""
        )
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":domain"))
    testImplementation(libs.junit)

    // Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter)

    // Retrofit and OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation (libs.okhttp.signpost)
    implementation (libs.signpost.core)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}