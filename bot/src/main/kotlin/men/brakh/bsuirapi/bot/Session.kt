package men.brakh.bsuirapi.bot

import men.brakh.bsuirapi.bot.api.Api
import men.brakh.bsuirapi.bot.config.StringsConfig

enum class SessionState(private val index: Int) {
    SELECT_BUILDING(0),
    SELECT_FLOOR(1),
    SELECT_DATE(2),
    SELECT_TIME(3);


    fun next(): SessionState {
        return values()[(this.index + 1) % values().size]
    }
}

class Session(private val userId: Int, private val bot: AbstractBot) {
    private var state: SessionState = SessionState.SELECT_BUILDING
    private var request: FreeAudsRequest? = null

    init {
        bot.sendBuildingsList(userId)
    }

    fun process(message: String) {
        when (state) {
            SessionState.SELECT_BUILDING -> {
                val building = extractInt(message)
                if (building == null) {
                    bot.sendErrorMessage(userId, StringsConfig.INVALID_INPUT_STRING)
                    return
                }

                request = FreeAudsRequest(building)
                bot.sendFloorsList(userId = userId, buildingNumber = building)
            }
            SessionState.SELECT_FLOOR -> {
                val floor = extractInt(message)
                request?.floor = floor
                bot.requestDate(userId)
            }
            SessionState.SELECT_DATE -> {
                request?.date = extractDate(message)
                bot.sendTimesList(userId)
            }
            SessionState.SELECT_TIME -> {
                request?.time = extractTime(message)
                val auds = Api.getFreeAuds(request!!)
                bot.sendFreeAudsList(userId, auds)
                bot.sendBuildingsList(userId)
            }
        }

        state = state.next()
    }

    private fun extractDate(str: String): String? {
        val regex = "\\d+\\.\\d+\\.\\d{4}".toRegex()

        return regex.find(str)?.value
    }

    private fun extractInt(str: String): Int? {
        return str.split(" ").firstOrNull()?.toIntOrNull()
    }

    /**
     * Time in format Lesson 1 (12:30-14:20)
     *
     * Note: this method return time of start lesson + 1 minute
     */
    internal fun extractTime(str: String): String? {
        val regex = "\\d+:\\d+".toRegex()
        val result = regex.find(str)
        val time = result?.value ?: return null

        val numbers = time.split(":").mapNotNull { it.toIntOrNull() }

        if (numbers.size != 2) return null

        return "${numbers[0]}:${numbers[1] + 1}"
    }
}
