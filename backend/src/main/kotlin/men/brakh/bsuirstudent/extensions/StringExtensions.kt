package men.brakh.bsuirstudent.extensions

fun String.getNotEmpty(): String? = if(this == "") null else this
