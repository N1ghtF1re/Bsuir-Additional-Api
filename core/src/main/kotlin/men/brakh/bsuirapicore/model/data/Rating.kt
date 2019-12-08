package men.brakh.bsuirapicore.model.data

data class RatingCheckPoint(val number: Int, val averageGrade: Double, val missedHours: Int)

data class RatingItem(val recordBookNumber: String, val averageGrade: Double, val missedHours: Int,
                      val averageShift: Double, val checkPoint: List<RatingCheckPoint>)