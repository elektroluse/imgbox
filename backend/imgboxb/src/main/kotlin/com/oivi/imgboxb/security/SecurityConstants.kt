package com.oivi.imgboxb.security

object SecurityConstants {

    /*
        Just for dev, in prod secret should be
        an environment variable
     */

    const val JWT_EXPIRATION : Long = 10000 + (60000 * 60 * 24)
    const val JWT_SECRET : String = "somesecretalskdjaskldasjdlkasdjsakldasjdlaskdjasldkjasdklasjdasd"

}