plugins {
	val kotlinVersion = "2.0.0"
	id("org.jetbrains.kotlin.jvm") version kotlinVersion
	signing
	`maven-publish`
}

val miniOrmGroupId = "me.saro"
val miniOrmArtifactId = "mini-orm"
val miniOrmVersion = "0.0.1"

repositories {
	mavenCentral()
}

java {
	withJavadocJar()
	withSourcesJar()
}

dependencies {
	// test
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

publishing {
	publications {
		create<MavenPublication>("maven") {

			groupId = miniOrmGroupId
			artifactId = miniOrmArtifactId
			version = miniOrmVersion

			from(components["java"])

			repositories {
				maven {
					credentials {
						try {
							username = project.property("sonatype.username").toString()
							password = project.property("sonatype.password").toString()
						} catch (e: Exception) {
							println("warn: " + e.message)
						}
					}
					val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
					val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
					url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
				}
			}

			pom {
				name.set("SARO MINI ORM")
				description.set("SARO MINI ORM")
				url.set("https://saro.me")

				licenses {
					license {
						name.set("The Apache License, Version 2.0")
						url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
					}
				}
				developers {
					developer {
						name.set("PARK Yong Seo")
						email.set("j@saro.me")
					}
				}
				scm {
					connection.set("scm:git:git://github.com/saro-lab/mini-orm.git")
					developerConnection.set("scm:git:git@github.com:saro-lab/mini-orm.git")
					url.set("https://github.com/saro-lab/mini-orm")
				}
			}
		}
	}
}

signing {
	sign(publishing.publications["maven"])
}

tasks.withType<Javadoc>().configureEach {
	options {
		this as StandardJavadocDocletOptions
		addBooleanOption("Xdoclint:none", true)
	}
}

configure<JavaPluginExtension> {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<Test> {
	useJUnitPlatform()
}
