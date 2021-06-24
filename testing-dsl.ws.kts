class Request(val query: String, val contentType: String)
class StatusResult(val statusCode: Int, val statusText: String)
class Response(val statusResult: StatusResult)
class RouteHandler(val request: Request, val response:Response)
fun get(path: String, func: RouteHandler.()->Unit) = func

fun main(args: Array<String>) {
    val xx =get("/hello") {
        response { statusCode = 405 }
    }
    xx(RouteHandler(Request(),Response())
}
