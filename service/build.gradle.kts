import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("nu.studer.jooq") version "9.0"
}

sourceSets {
    main {
        kotlin {
            srcDir("build/generated-src/jooq/main")
        }
    }
}

dependencies {
    implementation(project(":objects"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    jooqGenerator("org.jooq:jooq-meta-extensions:3.18.11")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

jooq {
    version.set("3.18.11")
    configurations {
        create("main") {
            jooqConfiguration.apply {
                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    database.apply {
                        name = "org.jooq.meta.extensions.ddl.DDLDatabase"
                        properties.add(
                            org.jooq.meta.jaxb.Property()
                                .withKey("scripts")
                                .withValue("src/main/resources/db/migration/*.sql")
                        )
                        properties.add(
                            org.jooq.meta.jaxb.Property()
                                .withKey("sort")
                                .withValue("flyway")
                        )
                        properties.add(
                            org.jooq.meta.jaxb.Property()
                                .withKey("defaultNameCase")
                                .withValue("lower")
                        )
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                        isDaos = true
                    }
                    target.apply {
                        packageName = "openspace.bones.generated"
                        directory = "build/generated-src/jooq/main"
                    }
                }
            }
        }
    }
}

tasks.named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq") {
    inputs.files(fileTree("src/main/resources/db/migration"))
        .withPropertyName("migrations")
        .withPathSensitivity(PathSensitivity.RELATIVE)
    allInputsDeclared.set(true)
}

springBoot {
    mainClass.set("openspace.bones.ApplicationKt")
}
