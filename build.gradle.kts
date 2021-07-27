import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    var kotlinVer = "1.4.21"

    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"

    kotlin("jvm") version kotlinVer
    kotlin("plugin.spring") version kotlinVer
    kotlin("plugin.jpa") version kotlinVer
    kotlin("kapt") version kotlinVer
    kotlin("plugin.allopen") version kotlinVer
    kotlin("plugin.noarg") version kotlinVer
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}


apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")
apply(plugin = "java")
apply(plugin = "kotlin")
apply(plugin = "kotlin-kapt")
apply(plugin = "kotlin-jpa")
apply(plugin = "kotlin-spring")

the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

val queryDslVer = "4.4.0"
val loggerVer = "2.0.4"

dependencies {
    implementation("com.google.firebase:firebase-admin:7.1.0")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.auth0:java-jwt:3.12.0")
    implementation("org.modelmapper:modelmapper:2.3.9")

    api("com.querydsl:querydsl-jpa:$queryDslVer")
    kapt("com.querydsl:querydsl-apt:$queryDslVer:jpa")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    implementation("io.github.microutils:kotlin-logging:$loggerVer")
    implementation("org.apache.commons:commons-lang3")
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.6.3")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("mysql:mysql-connector-java")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
    implementation ("org.springframework.boot:spring-boot-starter-oauth2-client")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
