package men.brakh.bsuirstudent.application.errorhandling

import men.brakh.bsuirstudent.application.exception.BadRequestException
import men.brakh.bsuirstudent.application.exception.IisException
import men.brakh.bsuirstudent.application.exception.ResourceNotFoundException
import men.brakh.bsuirstudent.application.exception.UnauthorizedException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ErrorControllerAdvisor : ResponseEntityExceptionHandler() {
  private val log = LoggerFactory.getLogger("ControllerAdvisor")

  @ExceptionHandler(ResourceNotFoundException::class)
  fun handleResourceNotFoundException(ex: ResourceNotFoundException, request: WebRequest): ResponseEntity<Any> {
    return processUnhandledException(ex, request, HttpStatus.NOT_FOUND)
  }

  @ExceptionHandler(BadRequestException::class)
  fun handleBadRequestException(
    ex: BadRequestException,
    request: WebRequest
  ): ResponseEntity<Any> {
    return processUnhandledException(ex, request, HttpStatus.BAD_REQUEST)
  }

  @ExceptionHandler(UnauthorizedException::class)
  fun handleUnauthorizedException(
    ex: UnauthorizedException,
    request: WebRequest
  ): ResponseEntity<Any> {
    return processUnhandledException(ex, request, HttpStatus.UNAUTHORIZED)
  }

  @ExceptionHandler(AccessDeniedException::class)
  fun handleForbiddenException(
    ex: AccessDeniedException,
    request: WebRequest
  ): ResponseEntity<Any> {
    return processUnhandledException(ex, request, HttpStatus.FORBIDDEN)
  }

  @ExceptionHandler(IisException::class)
  fun handleIisException(
    ex: IisException,
    request: WebRequest
  ): ResponseEntity<Any> {
    return processUnhandledException(ex, request, HttpStatus.I_AM_A_TEAPOT, message = "Iis is down")
  }

  @ExceptionHandler(MaxUploadSizeExceededException::class)
  fun handleSizeLimitException(
    ex: MaxUploadSizeExceededException,
    request: WebRequest
  ): ResponseEntity<Any> {
    return processUnhandledException(ex, request, HttpStatus.PAYLOAD_TOO_LARGE, message = "Maximum upload size exceeded")
  }
  @ExceptionHandler(Exception::class)
  fun handleAnyException(
    ex: Exception,
    request: WebRequest
  ): ResponseEntity<Any> {
    log.error("Unhandler exception", ex)
    return processUnhandledException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR)
  }

  private fun processUnhandledException(
    ex: Exception,
    request: WebRequest,
    status: HttpStatus,
    message: String? = null
  ): ResponseEntity<Any> {
    if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
      log.error("Unhandler exception", ex)
    } else {
      log.warn("Exception: ", ex)

    }
    val error = ErrorResponse(message ?: ex.message ?: "Unhandler error")
    return ResponseEntity(error, status)
  }
}