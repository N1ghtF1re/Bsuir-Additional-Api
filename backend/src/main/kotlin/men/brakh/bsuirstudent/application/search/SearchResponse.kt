package men.brakh.bsuirstudent.application.search;

import men.brakh.bsuirstudent.domain.Dto

data class SearchResponse<D : Dto> (
  val totalPages: Int,
  val page: Int,
  val pageSize: Int,
  val content: List<D>
)