package men.brakh.bsuirstudent.domain.iis.student.settings

import men.brakh.bsuirstudent.domain.iis.student.UserSettings
import org.springframework.data.jpa.repository.JpaRepository

interface UserSettingsRepository : JpaRepository<UserSettings, Int>