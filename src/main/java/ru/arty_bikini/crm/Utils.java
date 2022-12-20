package ru.arty_bikini.crm;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;
import java.util.TimeZone;
import java.util.stream.Stream;

public class Utils {
    //хеш
    public static String SHA256(String data){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            String hashString = Base64.getEncoder().encodeToString(hash);

            return hashString;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
    public static String SHA256(File file){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            byte[] data = new byte[1024 * 1024];
    
            InputStream input = new FileInputStream(file);
            try (input) {
                while (true) {
                    int size = input.read(data);
                    if (size < 0) {
                        break;
                    }
    
                    digest.update(data, 0, size);
                }
            }
            
            byte[] hash = digest.digest();

            String hashString = Base64.getEncoder().encodeToString(hash);

            return hashString;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    
    }

    //генерирует пароли
    public static String generatePassword() {

        Random random = new Random(System.currentTimeMillis());

        StringBuilder password = new StringBuilder();

        String[] tokensA = "01234567890".split("");
        String[] tokensB = "qwertyuiopasdfghjklzxcvbnm".split("");

        for (int i = 0; i < 4; i++) {
            String token = tokensA[random.nextInt(tokensA.length)];
            password.append(token);
        }

        for (int i = 0; i < 2; i++) {
            String token = tokensB[random.nextInt(tokensB.length)];
            password.append(token);
        }

        for (int i = 0; i < 4; i++) {
            String token = tokensA[random.nextInt(tokensA.length)];
            password.append(token);
        }

        return password.toString();
    }

    //из LocalDateTime в long
    public static long toLongFromTime(LocalDateTime LocalDate){
        return LocalDate.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    //из long в LocalDateTime
    public static LocalDateTime toTime(long miliseconds){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(miliseconds), ZoneOffset.UTC);
    }

    //из LocalDate в long
    public static long toLong(LocalDate LocalDate){
        return LocalDate.atTime(LocalTime.of(0, 0)).toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    //из long в LocalDate
    public static LocalDate toDate(long miliseconds){
        return LocalDate.ofInstant(Instant.ofEpochMilli(miliseconds), ZoneOffset.UTC);
    }

    //из строки в LocalDateTime  14.11.2022 11:36:41
    public static LocalDateTime toTime(String str){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        return dateTime;
    }
    
    public static LocalDate toDate(String str){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate dateTime = LocalDate.parse(str, formatter);
        return dateTime;
    }
}
