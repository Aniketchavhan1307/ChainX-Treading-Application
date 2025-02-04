package com.treading.config;


public class JwtConstant 
{
	public static final String SECRETE_KEY = "YourSuperSecretKeyWithEnoughLength1234!";

	public static final String JWT_HEADER =	"Authorization";
}



//============================================================
//
//import io.jsonwebtoken.security.Keys;
//import java.nio.charset.StandardCharsets;
//import javax.crypto.SecretKey;
//
//public class JwtConstant {
//    public static final SecretKey SECRETE_KEY = 
//        Keys.hmacShaKeyFor("YourSuperSecretKeyWithEnoughLength1234!".getBytes(StandardCharsets.UTF_8));
//
//    public static final String JWT_HEADER = "Authorization";
//}
