plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.joosulsa"
    compileSdk = 34

    viewBinding{enable=true }

    defaultConfig {
        applicationId = "com.example.joosulsa"
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
}

dependencies {

    implementation ("androidx.work:work-runtime:2.7.0")
    implementation("com.android.volley:volley:1.2.1") // 네트워크 통신 라이브러리 추가
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("de.hdodenhof:circleimageview:3.1.0") // 이미지 동그랗게 만들어주는 라이브러리 사용법 my_page_info
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0") // 유튜브 화면 뷰
    // Kakao maps sdk(형식이 바뀜...웹에 있는 방식으로는 안됨.)
    // implementation (files("libs/libDaumMapAndroid.jar"))


}