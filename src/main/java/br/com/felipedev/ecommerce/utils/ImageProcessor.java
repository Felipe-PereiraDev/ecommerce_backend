package br.com.felipedev.ecommerce.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ImageProcessor {

    public static String resizeAndConvertToBase64(byte[] originalBytes, int maxSize, float quality, String format) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Thumbnails.of(new ByteArrayInputStream(originalBytes))
                    .size(maxSize, maxSize)
                    .outputQuality(quality) // 0.0 a 1.0
                    .outputFormat(format)
                    .toOutputStream(outputStream);
            return Base64.getEncoder()
                    .encodeToString(outputStream.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar imagem", e);
        }
    }
}
