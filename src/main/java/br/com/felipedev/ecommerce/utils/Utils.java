package br.com.felipedev.ecommerce.utils;

public abstract class Utils {

    public static String formatCode(String code) {
        return code.trim().replaceAll("\\s", "").toUpperCase();
    }
}
