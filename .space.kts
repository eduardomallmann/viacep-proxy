job("Example shell script") {
    container(displayName = "Build Image", image = "openjdk:17") {
        shellScript {
            content = """
                chmod +x mvnw
                ./mvnw spring-boot:build-image
            """
        }
    }
}