package br.com.toplibrary.controller.exception;

import java.util.Map;

public class ErrorMessage {

    public static Map<String, String> send(String message) {
        return Map.of("message", message);
    }

}
