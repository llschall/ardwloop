**ardwloop** takes care of exchanging a matrix of data between your Java (or Kotlin) program and its matching Arduino program.

Work in progress...

Setup:

repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/llschall/ardwloop")
    }
}

dependencies {
    implementation 'io.github.llschall:ardwloop:0.0.1'
}
