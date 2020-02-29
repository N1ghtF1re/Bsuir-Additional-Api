package men.brakh.bsuirapi.bot

import men.brakh.bsuirapi.bot.api.Api
import men.brakh.bsuirapi.bot.api.AuditoriumDto
import men.brakh.bsuirapi.bot.config.AuditoriumTypeDescription
import men.brakh.bsuirapi.bot.config.LessonsTime
import men.brakh.bsuirapi.bot.config.StringsConfig
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class AbstractBot(threadsCount: Int) {
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(threadsCount)
    private val sessions: MutableMap<Int, Session> = mutableMapOf()

    fun sendBuildingsList(userId: Int) {
        val buildingsList = Api.getBuildings()
                .map { buildingObject -> "${buildingObject.name} ${StringsConfig.BUILDING}" }

        sendList(userId, buildingsList, StringsConfig.SELECT_BUILDING)
    }
    fun sendFloorsList(userId: Int, buildingNumber: Int) {
        val building = Api.getBuildings()
                .find { building -> building.name == buildingNumber }

        if (building == null) {
            sendErrorMessage(userId, StringsConfig.INVALID_INPUT_STRING)
            return
        }

        val floorsList = building.floors
                .map { "$it ${StringsConfig.FLOOR}"} + StringsConfig.ANY


        sendList(userId, floorsList, StringsConfig.SELECT_FLOOR)
    }
    fun sendTimesList(userId: Int) {
        sendList(userId, LessonsTime.times, StringsConfig.SELECT_TIME)
    }

    fun requestDate(userId: Int) {
        sendList(userId, listOf(StringsConfig.TODAY), StringsConfig.SELECT_DATE)
    }

    fun sendFreeAudsList(userId: Int, auds: List<AuditoriumDto>) {
        val response = auds.groupBy { it.type }
                .asSequence()
                .map { (type, list) -> AuditoriumTypeDescription.get(type) to list }
                .map { (description, list) -> description + "\n" + list
                        .joinToString(separator = "\n") { auditorium
                            -> "- ${auditorium.name}-${auditorium.building}" } }
                .joinToString(separator = "\n\n")

        sendMessage(userId, response)
    }

    open fun handleMessage(userId: Int, msg: String) {
        if (userId !in sessions) {
            sessions[userId] = Session(userId, this)
        } else {
            val session = sessions[userId]!!

            threadPool.execute {
                session.process(msg)
            }
        }
    }

    open fun sendErrorMessage(userId: Int, message: String) = sendMessage(userId, message)


    abstract fun sendMessage(userId: Int, message: String)
    abstract fun sendList(userId: Int, values: List<String>, message: String)
}