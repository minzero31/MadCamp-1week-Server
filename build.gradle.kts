buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }
}

allprojects {
    // 프로젝트 레벨에서는 repositories 블록을 제거합니다.
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
