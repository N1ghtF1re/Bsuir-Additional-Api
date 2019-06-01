package men.brakh.newsparser.model

import men.brakh.newsparser.model.parser.Parser
import org.slf4j.LoggerFactory
import java.io.File
import java.lang.reflect.Modifier


object ParsersLoader {
    private val logger = LoggerFactory.getLogger(ParsersLoader::class.java)
    private val <T> Class<T>.isAbstact: Boolean
        get() =  Modifier.isAbstract( this.modifiers )


    fun load(): List<Class<Parser>> {
        val currentPackage = Parser::class.java.getPackage().name

        val root = Thread.currentThread().contextClassLoader.getResource(currentPackage.replace(".", "/"))

        return File(root.file)
                .listFiles()
                .filter { file -> file.isDirectory } // Directories in model/parser
                .flatMap { file -> file.listFiles { _, name -> name.endsWith(".class") }.toList() }
                .mapNotNull { file ->
                    val className = file.name.replace(".class$".toRegex(), "")
                    val relativePackage = file.parentFile.name
                    try {
                        val fullClassName = "$currentPackage.$relativePackage.$className"
                        Class.forName(fullClassName)
                                .takeIf { clazz ->  !clazz.isAbstact && Parser::class.java.isAssignableFrom(clazz) }
                                ?.let { clazz -> println("$clazz loaded"); clazz as Class<Parser> }
                    } catch (e: ClassNotFoundException) {
                        println("Loading class $className error")
                        e.printStackTrace()
                        null
                    }
                }
    }
}
