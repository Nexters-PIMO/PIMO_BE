package com.nexters.pimo.common.utils

import com.nexters.pimo.common.constants.CommCode
import com.nexters.pimo.common.exception.BadRequestException
import io.netty.buffer.ByteBufUtil
import io.netty.buffer.Unpooled
import io.netty.handler.codec.base64.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


/**
 * @author yoonho
 * @since 2023.02.12
 */
object CipherUtil {
    private val cipher: Cipher = Cipher.getInstance("AES")
    private val keySpec: SecretKeySpec = SecretKeySpec("parkyoonhosecret".toByteArray(), "AES")

    fun encode(type: String, data: String): String {
        try{
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            val prefixedData = CommCode.findPrefix(type) + data
            return Base64.encode(Unpooled.wrappedBuffer(prefixedData.toByteArray())).toString(Charsets.UTF_8)
        }catch (e: Exception) {
            e.printStackTrace()
            throw BadRequestException("요청하신 정보를 암호화할 수 없습니다.")
        }
    }

    fun decode(data: String): String {
        try{
            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            return ByteBufUtil.getBytes(Base64.decode(Unpooled.wrappedBuffer(data.toByteArray()))).toString(Charsets.UTF_8)
        }catch (e: Exception) {
            e.printStackTrace()
            throw BadRequestException("요청하신 정보를 복호화할 수 없습니다.")
        }
    }
}