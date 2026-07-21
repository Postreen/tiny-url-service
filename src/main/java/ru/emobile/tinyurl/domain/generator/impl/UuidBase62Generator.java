package ru.emobile.tinyurl.domain.generator.impl;

import org.springframework.stereotype.Component;
import ru.emobile.tinyurl.domain.generator.ShortCodeGenerator;

import java.math.BigInteger;
import java.util.UUID;

@Component
public class UuidBase62Generator implements ShortCodeGenerator {

    private static final String BASE62 =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final int LENGTH = 8;

    @Override
    public String generate() {

        UUID uuid = UUID.randomUUID();
        BigInteger value = new BigInteger(
                uuid.toString()
                        .replace("-", ""),
                16
        );

        return encode(value)
                .substring(0, LENGTH);
    }


    private String encode(BigInteger value) {
        StringBuilder result = new StringBuilder();
        BigInteger base = BigInteger.valueOf(62);
        while (value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod =
                    value.divideAndRemainder(base);
            result.append(
                    BASE62.charAt(
                            divmod[1].intValue()
                    )
            );
            value = divmod[0];
        }
        return result.reverse().toString();
    }
}