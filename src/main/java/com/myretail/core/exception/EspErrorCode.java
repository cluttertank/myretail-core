package com.myretail.core.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Steve Murphy
 */
public enum EspErrorCode {

    // Common
    MULTI_PARTY, BLACKLIST, ADDRESS_CLEANSING_DOWN, ADDRESS_CLEANSING_FAILED, INVALID_CLIENT_ID,

    // Identity Type
    MISSING_FIRST_NAME, BAD_FIRST_NAME, BAD_MIDDLE_NAME, MISSING_LAST_NAME, BAD_LAST_NAME, BAD_NAME_PREFIX, BAD_NAME_SUFFIX,

    // Communication Method common
    MISSING_TYPE, BAD_TYPE,

    // Postal Address Type
    MISSING_ADDRESS_LINE_1, BAD_ADDRESS_LINE_1, BAD_ADDRESS_LINE_2, BAD_ADDRESS_LINE_3, MISSING_CITY, BAD_CITY, MISSING_STATE, BAD_STATE, MISSING_ZIP, BAD_ZIP, BAD_ZIP4, MISSING_COUNTRY, BAD_COUNTRY,

    // Email Type
    MISSING_EMAIL, BAD_EMAIL,

    // Phone Number Type
    MISSING_NUMBER, BAD_NUMBER, MISSING_AREA_CODE, BAD_AREA_CODE, BAD_EXTENSION, MISSING_COUNTRY_CODE, BAD_COUNTRY_CODE,

    // Account Type
    MISSING_ACCOUNT_NUMBER, BAD_ACCOUNT_NUMBER, MISSING_ACCOUNT_CATEGORY, BAD_ACCOUNT_CATEGORY, MISSING_ACCOUNT_SUB_CATEGORY,

    // Epsilon Response
    INSUFFICIENT_POINTS, INVALID_MEMBER, INACTIVE_MEMBER, SUSPICIOUS_MEMBER, INELIGIBLE_AWARD, INVALID_QUANTITY, LOCKED_MEMBER, PLACE_ORDER_FAILURE, ISSUE_ORDER_FAILURE, GENERATE_CERT_FAILURE, BBY_WEBSERVICE_FAILURE;

    private EspErrorCode() {
    }

    public static EspErrorCode fromString(String codeStr) {
        if (codeStr != null) {
            for (EspErrorCode customErrorCode : EspErrorCode.values()) {
                if (StringUtils.equalsIgnoreCase(customErrorCode.name(), codeStr)) {
                    return customErrorCode;
                }
            }
        }
        return null;
    }
}
