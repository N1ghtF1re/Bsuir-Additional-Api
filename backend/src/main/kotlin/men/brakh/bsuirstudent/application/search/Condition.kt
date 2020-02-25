package men.brakh.bsuirstudent.application.search

import java.text.DateFormat
import java.text.SimpleDateFormat


data class Condition(
     val type: Type,
     val comparison: Comparison,
     private val _value: Any,
     val field: String
) {

  val value: Any
    get() {
      try {
        when (type) {
          Type.numeric, Type.string, Type.bool, Type.uuid -> return _value
          Type.date -> return if (_value is String) {
            dateFormat.parse(_value.toString())
          } else {
            _value
          }
          Type.list -> {
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