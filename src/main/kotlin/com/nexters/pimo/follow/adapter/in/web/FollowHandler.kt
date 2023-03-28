package com.nexters.pimo.follow.adapter.`in`.web

import com.nexters.pimo.common.constants.CommCode
import com.nexters.pimo.common.dto.BaseResponse
import com.nexters.pimo.common.exception.BadRequestException
import com.nexters.pimo.common.filter.userId
import com.nexters.pimo.follow.adapter.`in`.web.dto.RegisterInput
import com.nexters.pimo.follow.application.dto.FollowAccountDto
import com.nexters.pimo.follow.application.dto.FollowCntDto
import com.nexters.pimo.follow.application.port.`in`.DeleteUseCase
import com.nexters.pimo.follow.application.port.`in`.FindUseCase
import com.nexters.pimo.follow.application.port.`in`.RegisterUseCase
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

/**
 * @author yoonho
 * @since 2023.02.16
 */
@Component
class FollowHandler(
    private val registerUseCase: RegisterUseCase,
    private val findUseCase: FindUseCase,
    private val deleteUseCase: DeleteUseCase
) {
    /**
     * 친구 등록
     *
     * @param userId [String]
     * @return boolean [Boolean]
     * @author yoonho
     * @since 2023.02.18
     */
    fun register(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(RegisterInput::class.java)
            .flatMap { registerUseCase.register(it.userId, request.userId()) }
            .flatMap { BaseResponse().success(it) }

    /**
     * 친구 취소
     *
     * @param userId [String]
     * @return boolean [Boolean]
     * @author yoonho
     * @since 2023.02.18
     */
    fun delete(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(RegisterInput::class.java)
            .flatMap { deleteUseCase.delete(it.userId, request.userId()) }
            .flatMap { BaseResponse().success(it) }

    /**
     * 친구 목록수 조회
     *
     * @return List [FollowCntDto]
     * @author yoonho
     * @since 2023.02.18
     */
    fun count(request: ServerRequest): Mono<ServerResponse> =
        findUseCase.count(request.queryParam("targetUserId").orElse(request.userId()))
            .flatMap { BaseResponse().success(it) }

    /**
     * 친구목록 계정정보 조회
     * <pre>
     *     1. path Variable
     *      - follower: 나만 친구추가한 친구목록 조회
     *      - followee: 상대방만 나를 친구추가한 친구목록 조회
     *      - followforfollow: 맞팔한 친구목록 조회
     *     2. sort
     *      - 0: 최근 친구추가한 순서
     *      - 1: 닉네임 가나다순
     *     3. paging
     *      - start: 시작Index
     *      - size: 해당 페이지에서 조회할 갯수
     *
     * @param sort [String]
     * @param start [String]
     * @param size [String]
     * @return List [FollowAccountDto]
     * @author yoonho
     * @since 2023.02.18
     */
    fun list(request: ServerRequest): Mono<ServerResponse> =
        when(request.pathVariable("target")) {
            "follower" -> {
                findUseCase.follower(
                    request.queryParam("targetUserId").orElse(request.userId()),
                    request.queryParam("sort").orElse(CommCode.DEFAULT_SORT_OPTION),
                    request.queryParam("start").orElse(CommCode.DEFAULT_PAGING_START).toInt(),
                    request.queryParam("size").orElse(CommCode.DEFAULT_PAGING_SIZE).toInt()
                )
            }
            "followee" -> {
                findUseCase.followee(
                    request.queryParam("targetUserId").orElse(request.userId()),
                    request.queryParam("sort").orElse(CommCode.DEFAULT_SORT_OPTION),
                    request.queryParam("start").orElse(CommCode.DEFAULT_PAGING_START).toInt(),
                    request.queryParam("size").orElse(CommCode.DEFAULT_PAGING_SIZE).toInt()
                )
            }
            "followforfollow" -> {
                findUseCase.followForFollow(
                    request.queryParam("targetUserId").orElse(request.userId()),
                    request.queryParam("sort").orElse(CommCode.DEFAULT_SORT_OPTION),
                    request.queryParam("start").orElse(CommCode.DEFAULT_PAGING_START).toInt(),
                    request.queryParam("size").orElse(CommCode.DEFAULT_PAGING_SIZE).toInt()
                )
            }
            else -> throw BadRequestException("유효한 경로가 아닙니다.")
        }
        .collectList()
        .flatMap { BaseResponse().success(it) }
}