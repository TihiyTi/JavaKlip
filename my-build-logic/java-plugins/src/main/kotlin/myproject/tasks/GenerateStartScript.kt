package myproject.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.PosixFilePermission.*

@CacheableTask
abstract class GenerateStartScript : DefaultTask() {

    @get:Input
    abstract val mainClass: Property<String>

    @get:OutputFile
    abstract val scriptFile: RegularFileProperty

    @TaskAction
    fun generate() {
        val main = mainClass.get() // String
        val out = scriptFile.get().asFile // java.io.File
        val script = "java -cp 'libs/*' $main"

        println("Out = $out")

        out.writeText(script)

        if (System.getProperty("os.name").lowercase().contains("win")) {
            println("POSIX permissions are not supported on Windows.")
        } else {
            Files.setPosixFilePermissions(
                out.toPath(),
                setOf(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_EXECUTE,
                    PosixFilePermission.GROUP_READ,
                    PosixFilePermission.GROUP_EXECUTE,
                    PosixFilePermission.OTHERS_READ,
                    PosixFilePermission.OTHERS_EXECUTE
                )
            )
        }
    }
}