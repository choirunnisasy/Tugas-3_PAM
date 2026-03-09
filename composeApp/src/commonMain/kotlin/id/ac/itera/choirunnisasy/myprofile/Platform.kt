package id.ac.itera.choirunnisasy.myprofile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform