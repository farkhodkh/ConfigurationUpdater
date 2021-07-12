plugins {
    id("com.android.application")
    kotlin("android")
//    kotlin("android.extensions")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    buildFeatures {
        viewBinding = true
    }

    lintOptions {
        disable ("TypographyFractions","TypographyQuotes", "UnsafeExperimentalUsageError", "UnsafeExperimentalUsageWarning")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    //TODO - add CI/CD
    //val gitInfo = GitInfo(project.projectDir)

    val applicationVersion = buildString(32) {
        append(ProjectInfo.versionName)

        val suffix1 = ""
//        val suffix1 = when {
//            gitInfo.isRelease -> ""
//            gitInfo.isReleaseCandidate -> "-${gitInfo.releaseCandidateSuffix}"
//            gitInfo.isHead -> "-${gitInfo.hash}"
//            else -> "-${gitInfo.branch}-${gitInfo.hash}"
//        }

        append(suffix1)

        append("-${ProjectInfo.buildNumber}")
    }

    defaultConfig {
        applicationId = ProjectInfo.applicationId
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.compileSdkVersion)
        versionCode = ProjectInfo.versionCode
        versionName = applicationVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
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

    sourceSets.getByName("androidTest") {
        assets.srcDirs(File("resources"))
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

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}")
    implementation("androidx.core:core-ktx:${Versions.androidxCoreKtx}")
    implementation("androidx.appcompat:appcompat:${Versions.androidxAppcompat}")
    implementation("com.google.android.material:material:${Versions.androidMaterial}")

    implementation("androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintlayout}")
    implementation("com.afollestad.material-dialogs:core:${Versions.materialDialogs}")
    implementation("com.afollestad.material-dialogs:files:${Versions.materialDialogs}")
    implementation("com.afollestad.material-dialogs:input:${Versions.materialDialogs}")

    // Activity Ktx for by viewModels()
    implementation("androidx.fragment:fragment-ktx:${Versions.fragmentKTX}")
    implementation("androidx.activity:activity-ktx:${Versions.activityKTX}")

    //ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Room
    implementation("androidx.room:room-runtime:${Versions.roomVersion}")
    kapt("androidx.room:room-compiler:${Versions.roomVersion}")
    implementation("androidx.room:room-ktx:${Versions.roomVersion}")

//    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:${Versions.hiltVersion}")
    kapt("androidx.hilt:hilt-compiler:${Versions.hiltJetpack}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}")
    kapt("com.google.dagger:hilt-compiler:${Versions.hiltVersion}")

    //Logger
    implementation("org.slf4j:slf4j-api:${Versions.slf4jVersion}")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersions}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersions}")

    //Work
    implementation("androidx.work:work-runtime-ktx:2.4.0")

    //Debug only
    debugImplementation("com.amitshekhar.android:debug-db:1.0.6")

    // Database (ROOM) - Test helpers
    testImplementation("androidx.room:room-testing:${Versions.roomVersion}")
    androidTestImplementation("androidx.room:room-testing:${Versions.roomVersion}")

    implementation(project(":core-model"))
    implementation(project(":feature-persistence-api"))
    implementation(project(":feature-persistence-room"))
    implementation(project(":feature-remote-config"))
}

kapt {
    correctErrorTypes = true
}

object ProjectInfo {
    const val group = "com.farkhodkhaknazarov"
    const val name = "configurationupdater"
    const val applicationId = "$group.tem.scheduler"
    const val buildNumber = 1

    const val version = "1.0.0"
    const val versionSuffix = "test1"

    val versionName = buildString {
        append(version)
        if (versionSuffix.isNotBlank()) {
            append("-")
            append(versionSuffix)
        }
    }

    const val versionCode = 1
}