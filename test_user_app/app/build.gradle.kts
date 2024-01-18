plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.test_user_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.test_user_app"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    dependencies {

        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.10.0")

        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

        //ROOM
        implementation ("androidx.room:room-runtime:2.2.0")
        annotationProcessor("androidx.room:room-compiler:2.2.0")

        //ViewModel and LiveData
        implementation ("androidx.lifecycle:lifecycle-extensions:2.1.0")
        annotationProcessor("androidx.lifecycle:lifecycle-compiler:2.1.0")

        //Recycler View
        implementation("androidx.recyclerview:recyclerview:1.2.1")
        implementation("com.google.android.material:material:1.4.0")

        implementation ("at.favre.lib:bcrypt:0.9.0")


    }
}