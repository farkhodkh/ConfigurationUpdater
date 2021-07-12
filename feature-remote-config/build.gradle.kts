import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    dependencies {
        implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*aar"))))
        implementation(project(":core-model"))
        implementation(project(":feature-persistence-api"))
        implementation("androidx.appcompat:appcompat:${Versions.androidxAppcompat}")

        //coroutines dependencies
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersions}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersions}")

        //Logger
        implementation("org.slf4j:slf4j-api:${Versions.slf4jVersion}")
        implementation("androidx.work:work-runtime-ktx:${Versions.workRuntime}")

        testImplementation ("junit:junit:${Versions.junitVersion}")
        testImplementation ("org.robolectric:robolectric:${Versions.robolectricVersion}")
        testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersions}")//1.5.1
    }
}

tasks {

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
        kotlinOptions.allWarningsAsErrors = true
    }

    val sourcesJar by creating(Jar::class.java) {
        archiveClassifier.set("sources")
        from(android.sourceSets["main"].java.srcDirs)
    }

}

object ProjectInfo {
    const val group = "com.farkhodkhaknazarov"
    const val name = "tem.scheduler"
    const val applicationId = "$group.tem.scheduler"

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