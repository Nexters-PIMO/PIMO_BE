package com.nexters.pimo.login.adapter.out

import com.nexters.pimo.common.constants.CommCode
import com.nexters.pimo.login.application.port.out.JwtTokenPort
import com.nexters.pimo.login.domain.TokenInfo
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
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
    private lateinit var webClient: WebClient

    @PostConstruct
    fun initWebClient() {
        this.webClient = webClientBuilder.build()
    }

    override fun createToken(code: String): Mono<TokenInfo> {
        try{
            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            params.add("grant_type", "authorization_code")
            params.add("client_id", "9991a02c1253223dd0fa649fab9e0df9")
            params.add("code", code)

            val uriComponents = UriComponentsBuilder
                .fromHttpUrl("https://kauth.kakao.com" + "/oauth/token")
                .queryParams(params)
                .build(false)

            return webClient.get()
                .uri(uriComponents.toUri())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TokenInfo::class.java)
        }catch (e: Exception) {
            throw e
        }
    }

    override fun support(state: String): Boolean =
        state == CommCode.Social.KAKAO.code
}