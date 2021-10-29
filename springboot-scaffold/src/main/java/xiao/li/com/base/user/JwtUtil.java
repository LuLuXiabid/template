package xiao.li.com.base.user;

import cn.hutool.core.codec.Base64Decoder;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import xiao.li.com.base.exception.BaseException;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
* jwt工具类 <br>
*
* @param null <br>
* @return  <br>
* @throws Exception <br>
* @author lixiao <br>
* @createDate 2021/10/29 11:13 <br>
**/
@Slf4j
public class JwtUtil {

    public static String createJwt(String priKeyBase64Str, String issuer, long minutes) {

        try {

            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64Decoder.decode(priKeyBase64Str));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(pkcs8KeySpec);

            JwtClaims claims =buildSignClaims(minutes, issuer);

            JsonWebSignature jws = new JsonWebSignature();
            jws.setPayload(claims.toJson());
            jws.setKey(privateKey);
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
            return jws.getCompactSerialization();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("RSA私钥处理错误", e);
            throw new BaseException("RSA私钥处理错误");
        } catch (JoseException e) {
            log.error("JWT加签错误", e);
            throw new BaseException("JWT加签错误");
        } catch (Exception e) {
            log.error("JWT加签未知异常", e);
            throw new BaseException("JWT加签未知异常");
        }
    }

    public static String createJwt(String priKeyBase64Str, String issuer, long minutes, CurrentUser currentUser){

        try{

            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64Decoder.decode(priKeyBase64Str));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = factory.generatePrivate(pkcs8KeySpec);

            JwtClaims claims =buildSignClaims(minutes, issuer);
            buildCustomClaims(claims, currentUser);

            JsonWebSignature jws = new JsonWebSignature();
            jws.setPayload(claims.toJson());
            jws.setKey(privateKey);
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
            return jws.getCompactSerialization();
        }catch (NoSuchAlgorithmException| InvalidKeySpecException e){
            log.error("RSA私钥处理错误", e);
            throw new BaseException("RSA私钥处理错误");
        }catch (JoseException e){
            log.error("JWT加签错误", e);
            throw new BaseException("JWT加签错误");
        }catch (Exception e){
            log.error("JWT加签未知异常", e);
            throw new BaseException("JWT加签未知异常");
        }

    }

    public static JwtClaims verifyJwt(String pubKeyBase64Str, String jwt){

        try{
            KeyFactory factory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(Base64Decoder.decode(pubKeyBase64Str));
            PublicKey publicKey = factory.generatePublic(encodedKeySpec);
            JwtConsumer consumer = buildVerifyConsumer(publicKey, null);
            JwtClaims claims = consumer.processToClaims(jwt);
            return claims;
        }catch (NoSuchAlgorithmException| InvalidKeySpecException e){
            log.error("公钥处理错误", e);
        }catch (InvalidJwtException e){
            log.error("jwt验签异常", e);
        }catch (Exception e) {
            log.error("jwt验签未知异常", e);
        }
        return null;
    }


    public static JwtClaims verifyJwt(String pubKeyBase64Str, String jwt, String subsystemCode){

        try{
            KeyFactory factory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(Base64Decoder.decode(pubKeyBase64Str));
            PublicKey publicKey = factory.generatePublic(encodedKeySpec);
            JwtConsumer consumer = buildVerifyConsumer(publicKey, subsystemCode);
            JwtClaims claims = consumer.processToClaims(jwt);
            return claims;
        }catch (NoSuchAlgorithmException| InvalidKeySpecException e){
            log.error("公钥处理错误", e);
        }catch (InvalidJwtException e){
            log.error("jwt验签异常", e);
        }catch (Exception e) {
            log.error("jwt验签未知异常", e);
        }
        return null;
    }




    private static JwtClaims buildSignClaims(long minutes, String issuer) throws BaseException {
        JwtClaims claims = new JwtClaims();
        claims.setExpirationTimeMinutesInTheFuture(minutes);
        claims.setIssuedAtToNow();
        claims.setIssuer(issuer);
        claims.setNotBeforeMinutesInThePast(1);
        return claims;

    }

    private static JwtConsumer buildVerifyConsumer(PublicKey publicKey, String issuer){

        JwtConsumer consumer = new JwtConsumerBuilder()
                .setRequireExpirationTime().setMaxFutureValidityInMinutes(5256000)
                .setExpectedIssuer(StringUtils.isNotBlank(issuer), issuer)
                .setAllowedClockSkewInSeconds(30)
                .setVerificationKey(publicKey)
                .setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, AlgorithmIdentifiers.RSA_USING_SHA256)
                .build(); // create the JwtConsumer instance

        return consumer;
    }

    private static JwtClaims buildCustomClaims(JwtClaims jwtClaims, CurrentUser currentUser){
        // additional claims/attributes about the subject can be added
        jwtClaims.setClaim("loginName", currentUser.getEmpNo());
        jwtClaims.setClaim("empNo", currentUser.getEmpNo());
        jwtClaims.setClaim("subsystem", currentUser.getSubsystem());
        jwtClaims.setClaim("authVersion", currentUser.getAuthVersion());
        jwtClaims.setClaim("userId", currentUser.getUserId());
        return jwtClaims;
    }
}
