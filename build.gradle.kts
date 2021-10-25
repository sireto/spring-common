import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
}

group = "io.sireto.spring"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-common")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	// web
	// swagger
	implementation("io.springfox:springfox-swagger2:3.0.0")
	implementation("io.springfox:springfox-swagger-ui:3.0.0")
	// JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.5.4")
	implementation("org.springframework.boot:spring-boot-starter-validation:2.5.4")
	// object mapper jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.5")
	//kotlin
	implementation("org.jetbrains.kotlin:kotlin-script-runtime:1.5.21")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	// gson
	implementation("com.google.code.gson:gson:2.8.8")
	// spring context
	implementation("org.springframework:spring-context-support:5.3.9")
	//lombok
	implementation("org.projectlombok:lombok:1.18.20")
	// jwt web token
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("com.auth0:java-jwt:3.18.1")
	//apache tika
	implementation("org.apache.tika:tika-parsers:2.1.0")
	implementation("org.apache.tika:tika-core:2.1.0")
	// commons io
	implementation("commons-io:commons-io")
	//test utils for postgres container
	implementation("org.testcontainers:postgresql:1.10.6")
	implementation("org.postgresql:postgresql:42.2.5")
	// test

//	testImplementation("org.springframework.boot:spring-boot-common-test")
	implementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework:spring-test:5.3.9")
	implementation("org.junit.jupiter:junit-jupiter:5.7.2")
	testImplementation("com.h2database:h2:1.4.200")
	testImplementation("org.springframework.security:spring-security-test:5.5.1")
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
