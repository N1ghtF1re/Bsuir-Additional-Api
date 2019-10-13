package men.brakh.bsuirapi.servlets.basic

import men.brakh.bsuirapi.AccessDeniedException
import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.NotFoundException
import men.brakh.bsuirapi.UnauthorizedException
import men.brakh.bsuirapi.inrfastructure.authorization.Permission
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

enum class HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    companion object {
        fun contains(value: String): Boolean {
            return values()
                    .map { it.toString() }
                    .any { it == value }
        }
    }
}

class HttpContext(val method: HttpMethod, val req: HttpServletRequest, val resp: HttpServletResponse)


abstract class BasicHttpServlet : HttpServlet() {
    protected val context: ThreadLocal<HttpContext?> = ThreadLocal()
    protected val handled: ThreadLocal<Boolean?> = ThreadLocal()

    /**
     * In case servlet needs to know url (for example, parameter in path)
     */
    protected inline fun <T : Any> urlHandling(block: BasicHttpServlet.(url: String) -> T): T {
        return block(context.get()!!.req.requestURL.toString())
    }

    protected inline fun <T : Any> response(block: BasicHttpServlet.(response: HttpServletResponse) -> T): T {
        return block(context.get()!!.resp)
    }

    protected inline fun <T : Any> multipleParameters(
            block: BasicHttpServlet.(parameters: Map<String, Array<String>>) -> T): T {
        return block(context.get()!!.req.parameterMap)
    }

    /**
     * Get request handling
     * @param <R> response type
     */
    protected inline fun <reified R : Any> get (block: BasicHttpServlet.(parameters: Parameters) -> R?) {
        val context = context.get() ?: return
        if (context.method == HttpMethod.GET) {
            handled.set(true)
            val result = block(Parameters(context.req.singleParameters))
            writeJson(result)
        }
    }


    /**
     * Post request handling
     * @param <C> creation request type
     * @param <R> response type
     */
    protected inline fun <reified C : Any, reified R : Any> post (block: BasicHttpServlet.(body: C) -> R?) {
        val context = context.get() ?: return
        if (context.method == HttpMethod.POST) {
            handled.set(true)

            val body = context.req.extractBody(C::class.java)
            val result = block(body)
            writeJson(result)
        }
    }

    /**
     * Put request handling
     * @param <U> updating request type
     * @param <R> response type
     */
    protected inline fun <reified U : Any, reified R : Any> put (block: BasicHttpServlet.(body: U) -> R?) {
        val context = context.get() ?: return
        if (context.method == HttpMethod.PUT) {
            handled.set(true)

            val body = context.req.extractBody(U::class.java)
            val result = block(body)

            writeJson(result)
        }
    }

    /**
     * Put request handling
     * @param <D> deleting request type
     * @param <R> response type
     */
    protected inline fun <reified D : Any, reified R : Any> delete (block: BasicHttpServlet.(body: D) -> R?) {
        val context = context.get() ?: return
        if (context.method == HttpMethod.DELETE) {
            handled.set(true)

            val body = context.req.extractBody(D::class.java)
            val result = block(body)

            writeJson(result)
        }
    }

    protected inline fun <reified T : Any> writeJson(content: T?) {
        if (T::class.java != Unit::class.java) {
            content?.let { context.get()!!.resp.writeJson(it) }
                    ?: throw NotFoundException("Entity isn't found")
        }
    }

    /**
     * Block will throw Unauthorized Exception if user isn't authenticated
     */
    protected inline fun <reified T : Any> authorized(block: BasicHttpServlet.() -> T): T {
        val authenticationManager = Config.authenticationManager

        if (!authenticationManager.isAuthenticated()) {
            throw UnauthorizedException()
        }

        return block()
    }

    protected inline fun <reified T : Any> hasPermission(permission: Permission, block: BasicHttpServlet.() -> T): T {
        val authenticationManager = Config.authenticationManager
        if (!authenticationManager.isGranded(permission)) {
            throw AccessDeniedException()
        }

        return block()
    }

    protected abstract fun handle()

    private fun basicHandle() {
        try {
            handled.set(false)
            handle()

            if (handled.get() != true) {
                context.get()!!.resp.writeError("Unsupported method",
                         HttpServletResponse.SC_METHOD_NOT_ALLOWED)
            }

        } catch (e: Exception) {
            ServletExceptionsHelper.handleError(e, context.get()!!.req, context.get()!!.resp)
        }
    }

    protected fun setDefaultHeanders(resp: HttpServletResponse) {
        resp.characterEncoding = "UTF-8"
        resp.setHeader("Access-Control-Allow-Origin", "*")
        resp.contentType = "application/json"
    }

    override fun service(req: HttpServletRequest, resp: HttpServletResponse) {
        setDefaultHeanders(resp)

        val httpMethodString = req.method

        if (HttpMethod.contains(httpMethodString)) {
            context.set(HttpContext(
                    method = HttpMethod.valueOf(httpMethodString),
                    req = req,
                    resp = resp
            ))
        }

        basicHandle()

        context.remove()
    }
}
