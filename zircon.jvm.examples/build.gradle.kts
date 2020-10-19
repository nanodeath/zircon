plugins {
    kotlin("jvm")
    application
}

base.archivesBaseName = "zircon.jvm.examples"

application {
    mainClassName = "org.hexworks.zircon.benchmark.SwingBenchmark"
}

kotlin {
    target {
        jvmTarget(JavaVersion.VERSION_1_8)
    }

    dependencies {
        with(Projects) {
            api(zirconCore)
            api(zirconJvmSwing)
            api(zirconJvmLibgdx)
            api("org.openjdk.jol:jol-core:0.13")
        }

        with (Libs) {
            api(gdx)
            api(gdxBox2D)
            api(gdxBackendLwjgl)
            api(gdxPlatform)
            api(gdxBox2DPlatform)
        }
    }
}
