plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.compileSdkVersion)
        versionCode = ProjectInfo.versionCode
        versionName = ProjectInfo.versionName

        setProperty("archivesBaseName", "${ProjectInfo.group}-${ProjectInfo.name}")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*aar"))))
    implementation(project(":core-model"))
    implementation(project(":feature-persistence-api"))

    // Room
    implementation("androidx.room:room-runtime:${Versions.roomVersion}")
    kapt("androidx.room:room-compiler:${Versions.roomVersion}")
    implementation("androidx.room:room-ktx:${Versions.roomVersion}")
}

object ProjectInfo {
    const val group = "com.farkhodkhaknazarov"
    const val name = "persistence.room"
    const val applicationId = "$group.persistence.room"

    const val version = "1.0.0"
    const val versionSuffix = "test1"

    val versionName = buildString {
        append(version)
        if(versionSuffix.isNotBlank()) {
            append("-")
            append(versionSuffix)
        }
    }

    const val versionCode = 1
}