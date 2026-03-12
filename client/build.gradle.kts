plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":objects"))
    implementation("org.springframework:spring-web:6.1.4")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")
}
