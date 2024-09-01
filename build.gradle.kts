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
	// drivers
	implementation("com.oracle.database.jdbc:ojdbc10:19.16.0.0")
	implementation("com.oracle.database.jdbc:ojdbc8:21.7.0.0")
	implementation("com.oracle.database.jdbc:ojdbc6:11.2.0.4")
	implementation("com.mysql:mysql-connector-j:9.0.0")
	implementation("org.postgresql:postgresql:42.7.4")
	implementation("com.microsoft.sqlserver:mssql-jdbc:11.2.1.jre11")
	implementation("com.ibm.db2:jcc:11.5.7.0")
	implementation("org.mariadb.jdbc:mariadb-java-client:3.0.6")
	implementation("com.sap.cloud.db.jdbc:ngdbc:2.14.7")
	implementation("com.ibm.informix:jdbc:4.50.8")
	implementation("org.firebirdsql.jdbc:jaybird:4.0.6.java11")
	implementation("org.hsqldb:hsqldb:2.7.3")
	implementation("com.h2database:h2:2.1.214")
	implementation("org.apache.derby:derby:10.17.1.0")

	// lib
	implementation("me.saro:kit:0.2.0")

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
