package io.sireto.spring.common.utils

import org.apache.tika.config.TikaConfig
import org.apache.tika.io.TikaInputStream.get
import org.apache.tika.metadata.Metadata
import org.springframework.web.multipart.MultipartFile

object FileUtils {
    private val tikaConfig: TikaConfig = TikaConfig.getDefaultConfig()

    fun getMimeType(file: MultipartFile):String{
        val metadata = Metadata()
        val inputStream = get(file.inputStream.readBytes(), metadata)
        val mediaType = tikaConfig.mimeRepository.detect(inputStream, metadata)
        val mimeType = tikaConfig.mimeRepository.forName(mediaType.toString())
        return mimeType.type.toString()
    }
}