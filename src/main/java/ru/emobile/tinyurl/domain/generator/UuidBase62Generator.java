package ru.emobile.tinyurl.domain.generator;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.UUID;

@Component
public class UuidBase62Generator implements ShortCodeGenerator {

    private static final String BASE62 =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Override
    public String generate() {
        UUID uuid = UUID.randomUUID();
        BigInteger number = uuidToBigInteger(uuid);
        return encode(number);
    }


    private BigInteger uuidToBigInteger(UUID uuid) {
        String hex = uuid.toString()
                .replace("-", "");
        return new BigInteger(hex, 16);
    }


    private String encode(BigInteger number) {
        StringBuilder result = new StringBuilder();
        BigInteger base = BigInteger.valueOf(62);
        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divmod = number.divideAndRemainder(base);
            result.append(
                    BASE62.charAt(
                            divmod[1].intValue()
                    )
            );
            number = divmod[0];
        }
        return result.reverse().toString();
    }
}
