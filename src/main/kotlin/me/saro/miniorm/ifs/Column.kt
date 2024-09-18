package me.saro.miniorm.ifs

@Target(
    AnnotationTarget.FIELD
)
@Retention(
    AnnotationRetention.RUNTIME
)
annotation class Column(
    val name: String = "",
    val unique: Boolean = false,
    val nullable: Boolean = true,
    val length: Int = 500,
)
