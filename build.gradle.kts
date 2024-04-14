import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}


repositories { mavenCentral() }

dependencies {
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
    implementation("org.springframework.boot", "spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")

    runtimeOnly("com.h2database", "h2")

    testImplementation("org.springframework.boot", "spring-boot-starter-test")
}

java { sourceCompatibility = JavaVersion.VERSION_17 }

tasks {
    withType<KotlinCompile> { kotlinOptions { jvmTarget = "17" } }
    withType<Test> { useJUnitPlatform() }
}
