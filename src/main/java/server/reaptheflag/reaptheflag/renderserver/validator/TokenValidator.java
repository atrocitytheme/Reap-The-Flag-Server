package server.reaptheflag.reaptheflag.renderserver.validator;

import server.reaptheflag.reaptheflag.renderserver.model.Validatable;

/**
 * This module is mainly used for token validation procedure, it parses the encoding value of the client,
 * check if the client meets the requirement or if the data is valid.
 * */
public interface TokenValidator {
    boolean validateToken(String token);

    default boolean isValidData(Validatable source) {
        return source.isVaildData();
    }
}
