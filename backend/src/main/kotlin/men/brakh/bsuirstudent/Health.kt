package men.brakh.bsuirstudent

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/heath")
class Health {
    @GetMapping
    @ResponseBody
    fun checkHealth(): Map<String, Boolean> {
        return mapOf("heath" to true)
    }
}