package men.brakh.newsparser.model

import men.brakh.newsparser.model.parser.Parser
import org.slf4j.LoggerFactory
import java.io.File
import java.lang.reflect.Modifier
import java.util.jar.JarFile



@SuppressWarnings("unchecked")
object ParsersLoader {
    private val logger = LoggerFactory.getLogger(ParsersLoader::class.java)

    private val <T> Class<T>.isAbstact: Boolean
        get() =  Modifier.isAbstract( this.modifiers )

    
    private fun loadClasses(classNames: List<String>): List<Class<Parser>> {
        return classNames.mapNotNull { classPath ->
            try {
                 Class.forName(classPath)
                .takeIf { clazz -> !clazz.isInterface && !clazz.isAbstact && Parser::class.java.isAssignableFrom(clazz) }
                        ?.let { clazz -> logger.info("$clazz loaded"); clazz as Class<Parser> }
            } catch (e: ClassNotFoundException) {
                logger.error("Loading class $classPath error", e)
                null
            }
        }

    }

    private fun loadFromJar(path: String, neededPackage: String): List<Class<Parser>> {
        val jarFile = JarFile(path)
        val e = jarFile.entries()

        val classNames = e.asSequence()
                .map { jarEntry -> jarEntry }
                .filter { jarEntry -> !jarEntry.isDirectory }
                .filter { jarEntry -> jarEntry.name.endsWith(".class") }
                .filter { jarEntry -> jarEntry.name.startsWith(neededPackage.replace(".", "/")) }
                .map { jarEntry -> jarEntry.name }
                .map { classPath -> classPath.removeSuffix(".class").replace("/", ".") }
                .toList()

        return loadClasses(classNames)
    }

    fun load(): List<Class<Parser>> {
        val currentPackage = Parser::class.java.getPackage().name

        val root = Thread.currentThread().contextClassLoader.getResource(currentPackage.replace(".", "/"))

        if (".jar!" in root.path) {
            val jarRoot = root.path.substringBeforeLast("!").removePrefix("file:")
            return loadFromJar(jarRoot, currentPackage)
        }

       return File(root.path)
                .listFiles()
                .filter { file -> file.isDirectory } // Directories in model/parser
                .flatMap { file -> file.listFiles { _, name -> name.endsWith(".class") }.toList() }
                .map { file ->
                    val className = file.name.removeSuffix(".class")
                    val relativePackage = file.parentFile.name
                    "$currentPackage.$relativePackage.$className"
                }
               .let { loadClasses(it) }
    }
}
