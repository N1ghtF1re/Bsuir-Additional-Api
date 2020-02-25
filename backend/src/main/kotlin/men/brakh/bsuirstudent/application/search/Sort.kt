package men.brakh.bsuirstudent.application.search;

enum class SortType {
  ASC, DESC
}

class Sort(
  val field: String,
  val type: SortType
)