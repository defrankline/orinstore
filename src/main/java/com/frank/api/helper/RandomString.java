package com.frank.api.helper;

import java.security.SecureRandom;

public class RandomString {
    /**
     * Generate a random string.
     */
    public String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public SecureRandom rnd = new SecureRandom();

    public String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
}
