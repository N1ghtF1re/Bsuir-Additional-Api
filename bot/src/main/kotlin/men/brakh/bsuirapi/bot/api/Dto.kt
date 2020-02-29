package men.brakh.bsuirapi.bot.api

data class AuditoriumDto(
        val name: String,
        val type: String,
        val floor: Int,
        val building: Int
)
data class BuildingDto(
        val name: Int,
        val floors: List<Int>
)

