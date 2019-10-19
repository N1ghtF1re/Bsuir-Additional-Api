package men.brakh.bsuirapi.servlets.api.v1.files

import men.brakh.bsuirapi.Config
import men.brakh.bsuirapi.app.file.model.*
import men.brakh.bsuirapi.servlets.basic.BasicHttpServlet
import java.io.OutputStream
import javax.servlet.annotation.MultipartConfig
import javax.servlet.annotation.WebServlet

private const val maxFileSize = 1024*1024*20L

@WebServlet("/api/v1/files/*", loadOnStartup = 1)
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024,
        maxFileSize=maxFileSize)
class FilesServlet : BasicHttpServlet() {
    private val fileService = Config.fileService

    private fun String.getId(countFromEnd: Int = 0): Long? {
        return this.split("/").dropLast(countFromEnd).last().toLongOrNull()
    }

    override fun handle() {
        get(urlPattern = ".*/files/maxSize$".toRegex()) {
            mapOf("maxSize" to maxFileSize)
        }

        get(urlPattern = ".*/files/\\d+$".toRegex()) {
            authorized {
                urlHandling { url ->
                    val id = url.getId()!!
                    fileService.getFile(id)
                }
            }
        }

        get(urlPattern = ".*/files/\\d+/download$".toRegex()) {
            response { response ->
                urlHandling { url ->
                    val id = url.getId(1)!!
                    val file = fileService.getFile(id)
                    val (mimeType, fileByteArray) = fileService.downloadFile(id)

                    response.contentType = mimeType
                    response.setHeader("Content-disposition","attachment; filename=${file.fileName}")
                    val out: OutputStream = response.outputStream

                    out.write(fileByteArray)
                    out.flush()
                }
            }
        }

        get(urlPattern = ".*/files/?$".toRegex()) {
            authorized {
                fileService.getAvailableFiles(null)
            }
        }

        postFile(urlPattern =  ".*/files(/\\d+)?/file$".toRegex()) { fileName, content ->
            authorized {
                urlHandling { url ->
                    val parentId = url.getId(1)
                    fileService.uploadFile(parentId = parentId, inputStream = content, filename = fileName)
                }
            }
        }

        post<DirectoryCreateRequest, Directory> (urlPattern = ".*/files(/\\d+)?/directory$".toRegex()) { body ->
            authorized {
                urlHandling { url ->
                    val parentId = url.getId(1)
                    fileService.createDirectory(body.fileName, parentId)
                }
            }
        }

        post<LinkCreateRequest, Link>(urlPattern = ".*/files(/\\d+)?/link$".toRegex()) { body ->
            authorized {
                urlHandling { url ->
                    val parentId = url.getId(1)
                    fileService.uploadLink(body.fileName, body.url, parentId)
                }
            }
        }

        put<FileUpdateRequest, AbstractFile>(urlPattern = ".*/files/\\d+$".toRegex()) { body ->
            authorized {
                urlHandling { url ->
                    val fileId = url.getId()!!
                    fileService.updateFileAccess(fileId, body.accessType)
                }
            }
        }
    }

}