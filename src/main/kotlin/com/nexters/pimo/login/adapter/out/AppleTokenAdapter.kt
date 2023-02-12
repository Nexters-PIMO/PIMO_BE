package com.nexters.pimo.login.adapter.out

import com.fasterxml.jackson.databind.ObjectMapper
import com.nexters.pimo.common.constants.CommCode
import com.nexters.pimo.common.exception.BadRequestException
import com.nexters.pimo.common.exception.NotFoundDataException
import com.nexters.pimo.login.application.port.out.JwtTokenPort
import com.nexters.pimo.login.domain.TokenInfo
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.io.Serializable
import java.math.BigInteger
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

/**
 * @author yoonho
 * @since 2023.01.26
 */
@Component
class AppleTokenAdapter(
    private val webClientBuilder: WebClient.Builder
): JwtTokenPort {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("\${login.keys.jwt}")
    private lateinit var secretKey: String
    @Value("\${login.apis.apple}")
    private lateinit var url: String

    private lateinit var webClient: WebClient

    @PostConstruct
    fun initWebClient() {
        this.webClient = webClientBuilder.build()
    }

    override fun createToken(code: String): Mono<TokenInfo> =
        findBy(code)
            .flatMap {
                val expiredDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant())

                val userId = Jwts.parser()
                    .setSigningKey(
                        KeyFactory.getInstance("RSA")
                            .generatePublic(
                                RSAPublicKeySpec(
                                    BigInteger(1, Base64.getUrlDecoder().decode(it.n)),
                                    BigInteger(1, Base64.getUrlDecoder().decode(it.e))
                                )
                            )
                    )
                    .parseClaimsJws(code).body.subject

                val token = Jwts.builder()
                    .setHeaderParams(mapOf(
                        "kid" to it.kid,
                        "alg" to it.alg
                    ))
                    .setIssuer("nexters-pimo")
                    .setIssuedAt(Date(System.currentTimeMillis()))
                    .setExpiration(expiredDate)
                    .setAudience("https://appleid.apple.com")
                    .setSubject("auth")
                    .setClaims(mapOf("userId" to userId))
                    .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.toByteArray()))
                    .compact()

                return@flatMap Mono.just(TokenInfo(accessToken = token, refreshToken = ""))
            }

    private fun findBy(code: String): Mono<ApplePublicKeys.ApplePublicKey> {
        val headerIdentityToken = code.substring(0, code.indexOf("."))
        val header = ObjectMapper().readValue(String(Base64.getDecoder().decode(headerIdentityToken), Charsets.UTF_8), Map::class.java)
        val kid = header["kid"].toString()
        val alg = header["alg"].toString()

        return webClient.get()
            .uri(url + "/auth/keys")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ApplePublicKeys::class.java)
            .map { toMatchedKey(it.keys, kid, alg) }
    }

    private fun toMatchedKey(keys: List<ApplePublicKeys.ApplePublicKey>, clientKid: String, clientAlg: String) =
        keys.find { it.kid == clientKid && it.alg == clientAlg } ?: throw BadRequestException("일치하는 공개키가 없습니다.")

    data class ApplePublicKeys(val keys: List<ApplePublicKey>) {
        data class ApplePublicKey (
            val kty: String,
            val kid: String,
            val use: String,
            val alg: String,
            val n: String,
            val e: String
        ): Serializable
    }

    override fun support(state: String): Boolean =
        state == CommCode.Social.APPLE.code

    override fun authToken(token: String): Mono<String> {
        return Mono.just(
            Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.toByteArray()))
                .parseClaimsJws(token)
                .body["userId"].toString()
        )
    }
}