package men.brakh.bsuirstudent.application.template

import men.brakh.bsuirstudent.application.exception.BadRequestException
import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.application.search.SearchRequest
import men.brakh.bsuirstudent.application.search.SearchResponse
import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.Dto
import org.springframework.data.domain.Page
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

class SearchTemplate<T : BaseEntity<out Any>, D : Dto>(
  private val presenter: EntityPresenter<T, D>,
  private val specificationExecutor: JpaSpecificationExecutor<T>
) {

  fun search(searchRequest: SearchRequest, dtoClass: Class<D>): SearchResponse<D> {
    return try {
      val page: Page<T> =
        specificationExecutor.findAll(searchRequest as Specification<T>, searchRequest.pageable)
      val content: List<D> = presenter.mapListToDto(page.content, dtoClass)

      SearchResponse(
        content = content,
        page = searchRequest.page,
        pageSize = searchRequest.pageSize,
        totalPages = page.totalPages
      );

    } catch (e: Exception) {
      throw BadRequestException(e)
    }
  }


}