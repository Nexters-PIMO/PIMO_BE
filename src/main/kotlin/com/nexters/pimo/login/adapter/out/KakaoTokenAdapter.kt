package com.nexters.pimo.login.adapter.out

import com.nexters.pimo.common.constants.CommCode
import com.nexters.pimo.common.exception.BadRequestException
import com.nexters.pimo.login.adapter.out.dto.KakaoTokenInfo
import com.nexters.pimo.login.application.port.out.JwtTokenPort
import com.nexters.pimo.login.domain.TokenInfo
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.01.26
 */
@Component
class KakaoTokenAdapter(
    private val webClientBuilder: WebClient.Builder
): JwtTokenPort {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("\${login.keys.clientId}")
    private lateinit var clientId: String
    @Value("\${login.apis.kauth}")
    private lateinit var kauthUrl: String
    @Value("\${login.apis.kapi}")
    private lateinit var kapiUrl: String

    private lateinit var webClient: WebClient

    @PostConstruct
    fun initWebClient() {
        this.webClient = webClientBuilder.build()
    }

    override fun createToken(code: String): Mono<TokenInfo> {
        try{
            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            params.add("grant_type", "authorization_code")
            params.add("client_id", clientId)
            params.add("code", code)

            val uriComponents = UriComponentsBuilder
                .fromHttpUrl( "$kauthUrl/oauth/token")
                .queryParams(params)
                .build(false)

            return webClient.get()
                .uri(uriComponents.toUri())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TokenInfo::class.java)
                .log()
        }catch (e: Exception) {
            throw e
        }
    }

    override fun support(state: String): Boolean =
        state == CommCode.Social.KAKAO.code

    override fun authToken(token: String): Mono<String> {
        try{
            val uriComponents = UriComponentsBuilder
                .fromHttpUrl("$kapiUrl/v1/user/access_token_info")
                .build(false)

            log.info(" >>> [authToken] request - token: $token, url: ${uriComponents.toUri()}")
            return webClient.get()
                .uri(uriComponents.toUri())
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KakaoTokenInfo::class.java)
                .log()
                .map { toUserId(it) }
        }catch (e: Throwable){
            throw e
        }
    }

    private fun toUserId(response: KakaoTokenInfo): String {
        if(response.code != null || response.msg != null) {
            throw BadRequestException("유효한 토큰이 아닙니다. - code: ${response.code}, msg: ${response.msg}")
        }
        return response.id
    }
}