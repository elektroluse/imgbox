package com.oivi.imgboxb.security

object SecurityConstants {

    /*
        Just for dev, in prod secret should be
        an environment variable
     */

    const val JWT_EXPIRATION : Long = 60000
    const val JWT_SECRET : String = "somesecret"

}