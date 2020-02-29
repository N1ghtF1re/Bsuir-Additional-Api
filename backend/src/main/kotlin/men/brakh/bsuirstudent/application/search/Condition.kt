package men.brakh.bsuirstudent.application.search

import java.text.DateFormat
import java.text.SimpleDateFormat


data class Condition(
     val type: Type,
     val comparison: Comparison,
     private val value: Any,
     val field: String
) {

  val fieldValue: Any
    get() {
      try {
        when (type) {
          Type.numeric, Type.string, Type.bool, Type.uuid -> return value
          Type.date -> return if (value is String) {
            dateFormat.parse(value.toString())
          } else {
            value
          }
          Type.list -> {
            value
          }
          Type.raw -> {
          }
        }
      } catch (e: Exception) {
        throw IllegalArgumentException("Invalid filter", e)
      }
      throw IllegalArgumentException()
    }

  companion object {
    @Transient
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  }
}