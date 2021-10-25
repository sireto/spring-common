import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
	id("maven-publish")
	id("org.jetbrains.dokka") version "1.4.10.2"
}

group = "com.github.sireto"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven{
		url = uri("https://jitpack.io")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
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

/**Source: https://github.com/nazmulidris/color-console/blob/main/build.gradle.kts **/
val developerName = "Sireto Technology"
val artifactId: String = rootProject.name
val artifactGroup: String = project.group.toString()
val artifactVersion: String = project.version.toString()

val githubUsername = "sireto"
val githubDescription = "A collection of common classes useful in Spring Boot development."
val githubHttpUrl = "https://github.com/${githubUsername}/${artifactId}"
val githubIssueTrackerUrl = "https://github.com/${githubUsername}/${artifactId}/issues"
val license = "Apache-2.0"
val licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"

val sourcesJar by tasks.creating(Jar::class) {
	archiveClassifier.set("sources")
	from(sourceSets.getByName("main").allSource)
	from("LICENCE.md") {
		into("META-INF")
	}
}

val dokkaJavadocJar by tasks.creating(Jar::class) {
	dependsOn(tasks.dokkaJavadoc)
	from(tasks.dokkaJavadoc.get().outputDirectory.get())
	archiveClassifier.set("javadoc")
}

// More info on `publishing`:
//   https://docs.gradle.org/current/userguide/publishing_maven.html#publishing_maven:resolved_dependencies
// More info on authenticating with personal access token (DeveloperId and ArtifactName must be lowercase):
//   https://docs.github.com/en/packages/guides/configuring-gradle-for-use-with-github-packages#authenticating-to-github-packages
publishing {
	repositories {
		maven {
			name = "GitHubPackages"
			url = uri("https://maven.pkg.github.com/${githubUsername}/${artifactId}")
			credentials {
				username = System.getenv("GITHUB_PACKAGES_USERID")
				password = System.getenv("GITHUB_PACKAGES_PUBLISH_TOKEN")
			}
		}
	}
}

publishing {
	publications {
		register("gprRelease", MavenPublication::class) {
			groupId = artifactGroup
			artifactId = artifactId
			version = artifactVersion

			from(components["java"])

			artifact(sourcesJar)
			artifact(dokkaJavadocJar)

			pom {
				packaging = "jar"
				name.set(artifactId)
				description.set(githubDescription)
				url.set(githubHttpUrl)
				scm {
					url.set(githubHttpUrl)
				}
				issueManagement {
					url.set(githubIssueTrackerUrl)
				}
				licenses {
					license {
						name.set(license)
						url.set(licenseUrl)
					}
				}
				developers {
					developer {
						id.set(githubUsername)
						name.set(developerName)
					}
				}
			}
		}
	}
}