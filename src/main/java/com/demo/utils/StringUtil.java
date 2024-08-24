package com.demo.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.security.SecureRandom;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

  public static String signKeyGenerator() {
    final String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    final int length = 32;

    Random random = new SecureRandom();
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int index = random.nextInt(character.length());
      builder.append(character.charAt(index));
    }
    return builder.toString();
  }
}
