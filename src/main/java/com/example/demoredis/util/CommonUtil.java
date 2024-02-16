package com.example.demoredis.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Component
@RequiredArgsConstructor
public class CommonUtil {

    private final MessageSource messageSource;

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    public static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <T> T toObject(String jsonString, Class<T> cls) {
        try {
            return objectMapper.readValue(jsonString, cls);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> toList(String jsonString, Class<T> cls) {
        try {
            return objectMapper.readValue(jsonString,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, cls));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 문자열 압축
     */
    public static String compress(String data) {
        if (data == null || data.length() == 0) return data;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(baos)) {
            gzipOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
            gzipOutputStream.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 문자열 압축 해제
     */
    public static String decompress(String data) {
        if (data == null || data.length() == 0) return data;
        byte[] compressedData = Base64.getDecoder().decode(data);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
             GZIPInputStream gzipInputStream = new GZIPInputStream(bais);
             InputStreamReader reader = new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                output.append(line);
            }

            return output.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 핸드폰 번호 생성 테스트 함수
    public static String[] generatePhoneNumbers(int count) {
        String[] phoneNumbers = new String[count];
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            StringBuilder phoneNumber = new StringBuilder("010");
            // 중간 자리(4자리)와 마지막 자리(4자리)를 무작위로 생성
            for (int j = 0; j < 8; j++) {
                phoneNumber.append(random.nextInt(10)); // 0부터 9까지의 숫자 중 하나를 추가
//                if (j == 3) { // 중간 자리와 마지막 자리 사이에 '-' 추가
//                    phoneNumber.append("-");
//                }
            }
            phoneNumbers[i] = phoneNumber.toString();
        }
        return phoneNumbers;
    }

    public static void main(String[] args) {
        String[] randomPhoneNumbers = generatePhoneNumbers(100000);
        // 결과 출력 (테스트를 위한 일부 번호만 출력)
        for (int i = 0; i < 10; i++) {
            System.out.println(randomPhoneNumbers[i]);
        }
        String cellPhoneNumbers = String.join(",", randomPhoneNumbers);
        System.out.println("Original String: " + cellPhoneNumbers.length());
        String compressed = compress(cellPhoneNumbers);
//        System.out.println("Compressed String: " + compressed);
        System.out.println("Compressed String length: " + compressed.length());

        String decompressed = decompress(compressed);
//        System.out.println("Decompressed String: " + decompressed);
        System.out.println("Decompressed String length: " + decompressed.length());
    }

    public static String getLocalHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    // https://linked2ev.github.io/java/2019/05/22/JAVA-1.-java-get-clientIP/
    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getHeader("Proxy-Client-IP");
        if (ip == null) ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip == null) ip = request.getHeader("HTTP_CLIENT_IP");
        if (ip == null) ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (ip == null) ip = request.getRemoteAddr();
        return ip;
    }

}
