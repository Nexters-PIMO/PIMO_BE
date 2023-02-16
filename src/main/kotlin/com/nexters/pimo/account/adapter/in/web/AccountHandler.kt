package com.nexters.pimo.account.adapter.`in`.web

import com.nexters.pimo.account.adapter.`in`.web.dto.AccountInput
import com.nexters.pimo.account.application.dto.AccountDto
import com.nexters.pimo.account.application.port.`in`.DeleteUserUseCase
import com.nexters.pimo.account.application.port.`in`.FindUserUseCase
import com.nexters.pimo.account.application.port.`in`.SaveUserUseCase
import com.nexters.pimo.common.dto.BaseResponse
import com.nexters.pimo.common.exception.BadRequestException
import com.nexters.pimo.common.filter.userId
import com.nexters.pimo.common.utils.AuthorizationUtil
import com.nexters.pimo.login.application.port.`in`.JwtTokenUseCase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.14
 */
@Component
class AccountHandler(
    private val findUserUseCase: FindUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * 내정보 저장하기
     *
     * @param nickname [String]
     * @param profileImgUrl [String]
     * @return AccountDto [AccountDto]
     * @author yoonho
     * @since 2023.02.15
     */
    fun saveUser(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(AccountInput::class.java)
            .flatMap { saveUserUseCase.saveUser(request.userId(), it.nickName, it.profileImgUrl) }
            .flatMap { BaseResponse().success(it) }

    /**
     * 내정보 조회
     *
     * @return AccountDto [AccountDto]
     * @author yoonho
     * @since 2023.02.15
     */
    fun findMe(request: ServerRequest): Mono<ServerResponse> =
        findUserUseCase.findUser(request.userId())
            .flatMap { BaseResponse().success(it) }

    /**
     * 사용자정보 조회
     *
     * @param userId [String]
     * @return AccountDto [AccountDto]
     * @author yoonho
     * @since 2023.02.18
     */
    fun findUser(request: ServerRequest): Mono<ServerResponse> =
        findUserUseCase.findUser(
            request.queryParam("userId").orElseThrow { BadRequestException("필수 입력값이 누락되었습니다.") }
        )
        .flatMap { BaseResponse().success(it) }


    /**
     * 사용자 조회 (by 넥네임)
     *
     * @param keyword [String]
     * @return List [AccountDto]
     * @author yoonho
     * @since 2023.02.15
     */
    fun searchUser(request: ServerRequest): Mono<ServerResponse> =
        findUserUseCase.searchUser(
            request.queryParam("nickname").orElseThrow { BadRequestException("필수 입력값이 누락되었습니다.") })
            .collectList()
            .flatMap { BaseResponse().success(it) }

    /**
     * 사용자정보 업데이트
     * <pre>
     *     1. 닉네임 수정
     *      - url: "/user/nickname"
     *      - query param: nickname
     *     2. 프로필사진URL 수정
     *      - url: "/user/profile"
     *      - query param: profile
     * </pre>
     *
     * @param nickname [String]
     * @param profile [String]
     * @return AccountDto [AccountDto]
     * @author yoonho
     * @since 2023.02.15
     */
    fun updateUser(request: ServerRequest): Mono<ServerResponse> =
        when(request.pathVariable("target")) {
            "nickname" -> saveUserUseCase.updateNickname(
                            request.userId(),
                            request.queryParam("nickname").orElseThrow { BadRequestException("필수 입력값이 누락되었습니다.") })
            "profile" -> saveUserUseCase.updateProfileImgUrl(
                            request.userId(),
                            request.queryParam("profile").orElseThrow { BadRequestException("필수 입력값이 누락되었습니다.") })
            else -> throw BadRequestException("유효한 경로가 아닙니다.")
        }
        .flatMap { BaseResponse().success(it) }

    /**
     * 사용자 계정 삭제
     *
     * @return boolean [Boolean]
     * @author yoonho
     * @since 2023.02.15
     */
    fun deleteUser(request: ServerRequest): Mono<ServerResponse> =
        deleteUserUseCase.deleteUser(request.userId())
            .flatMap { BaseResponse().success(it) }

    /**
     * 중복 닉네임 체크
     *
     * @param nickname [String]
     * @return boolean [Boolean]
     * @author yoonho
     * @since 2023.02.15
     */
    fun existsNickName(request: ServerRequest): Mono<ServerResponse> =
        findUserUseCase.existsNickname(
            request.queryParam("nickname").orElseThrow { BadRequestException("필수 입력값이 누락되었습니다.") })
            .flatMap { BaseResponse().success(it) }

    /**
     * 사용자 존재여부 체크
     *
     * @return boolean [Boolean]
     * @author yoonho
     * @since 2023.02.15
     */
    fun existUser(request: ServerRequest): Mono<ServerResponse> =
        findUserUseCase.existsUser(request.userId())
            .flatMap { BaseResponse().success(it) }

}