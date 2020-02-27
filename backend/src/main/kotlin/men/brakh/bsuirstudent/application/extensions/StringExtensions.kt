package men.brakh.bsuirstudent.application.extensions

fun String.getNotEmpty(): String? = if(this == "") null else this
