plugins {
    kotlin("multiplatform")
}

group = "me.canactacu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
    }
    js(BOTH) {
        browser {

        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting

        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
    }
}