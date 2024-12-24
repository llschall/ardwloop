import java.nio.file.Files
import java.nio.file.Paths

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
    `maven-publish`
    signing
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fazecast:jSerialComm:2.10.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(21)
}

tasks.named<Test>("test") {
    systemProperty("test.setup", "Allschwil")
    val folder = System.getProperty("user.dir")
    val path = Paths.get(folder).resolve("src/test/java/org/llschall/ardwloop/jni")
        .toAbsolutePath().toString()
    println("PATH is [$path]")
    systemProperty("java.library.path", path)
    useJUnitPlatform()
}

if (!project.hasProperty("token_usr")) {
    println("=== Publish skipped because no credential is provided. ===")
} else {
    val user: String = properties["token_usr"].toString()
    val pwd: String = properties["token_pwd"].toString()

    afterEvaluate {
        publishing {
            publications {
                create<MavenPublication>("ardwloop") {
                    from(components["java"])

                    groupId = "io.github.llschall"
                    artifactId = "ardwloop"
                    version = "0.2.7"

                    pom {
                        signing {
                            sign("publishing.publications.ardwloop")
                            sign("configurations.archives")
                        }
                        name = "ardwloop"
                        description =
                            "ardwloop takes care of exchanging a matrix of data between your Java (or Kotlin) program and its matching Arduino program."
                        url = "https://llschall.github.io/ardwloop"
                        licenses {
                            license {
                                name = "The Apache License, Version 2.0"
                                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                            }
                        }
                        developers {
                            developer {
                                id = "llschall"
                                name = "Laurent Schall"
                                email = "llschall@gmx.com"
                            }
                        }
                        scm {
                            url = "https://github.com/llschall/ardwloop.git"
                        }
                    }
                }
            }
            repositories {
                maven {
                    credentials {
                        username = user
                        password = pwd
                    }
                    url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
                }
            }
            signing {
                sign(publishing.publications["ardwloop"])
            }
        }
    }
}
