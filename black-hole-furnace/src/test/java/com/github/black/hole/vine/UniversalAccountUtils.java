package com.github.black.hole.vine;

import java.util.Arrays;

/**
 * @author hairen.long
 * @date 2021/5/18
 */
public class UniversalAccountUtils {
    public static final long NUMBER_ID_OFFSET = 100000000000L;
    public static final long NUMBER_ID_OFFSET_LIMIT = 1000000000000L;
    public static final long FAMILY_OFFSET = 10000000L;
    private static final int UNIVERSAL_ACCOUNT_ID_LENGTH = 15;

    public UniversalAccountUtils() {}

    public static Long generateUniversalAccountId(
            Long domainUserId, DomainAccountType domainAccountType) {
        boolean invalidParams =
                domainUserId == null
                        || domainAccountType == null
                        || domainUserId > 1000000000000L
                        || domainUserId < 1L;
        if (invalidParams) {
            throw new IllegalArgumentException(
                    String.format(
                            "domainType-%s,domainUserId:%d，账户类型不合法",
                            domainAccountType, domainUserId));
        } else {
            String uIdStr = generateIdStr(domainUserId, domainAccountType);
            return Long.parseLong(uIdStr);
        }
    }

    private static String generateIdStr(Long domainUserId, DomainAccountType domainAccountType) {
        if (DomainAccountType.FAMILY.equals(domainAccountType) && domainUserId < 10000000L) {
            domainUserId = domainUserId + 10000000L;
        }

        String numberId = getNumberId(domainUserId);
        String checksum = getChecksum(numberId);
        StringBuilder sb = new StringBuilder();
        sb.append(numberId).append(checksum);
        int value = domainAccountType.getValue();
        return value > 10 && value < 100
                ? sb.append(value).toString()
                : sb.append("0").append(value).toString();
    }

    public static Long generateUniversalAccountId4XY(Long domainUserId) {
        boolean invalidParams =
                domainUserId == null || domainUserId > 100000000000L || domainUserId < 1L;
        if (invalidParams) {
            throw new IllegalArgumentException(
                    String.format("domainUserId:%d，账户类型不合法", domainUserId));
        } else {
            DomainAccountType domainAccountType = DomainAccountType.COFFEE;
            if (domainUserId > 10000000L) {
                domainAccountType = DomainAccountType.FAMILY;
            }

            String uIdStr = generateIdStr(domainUserId, domainAccountType);
            return Long.parseLong(uIdStr);
        }
    }

    public static boolean validateUniversalId(long universalId) {
        String universalIdStr = String.valueOf(universalId);
        int length = universalIdStr.length();
        if (length != 15) {
            return false;
        } else {
            int type = Integer.valueOf(universalIdStr.substring(length - 2));
            DomainAccountType domainAccountType = DomainAccountType.fromValue(type);
            if (domainAccountType == null) {
                return false;
            } else {
                int checksum = Integer.valueOf(universalIdStr.substring(length - 3, length - 2));
                String domainId = universalIdStr.substring(0, length - 3);
                String correctChecksum = getChecksum(domainId);
                return correctChecksum.equals(String.valueOf(checksum));
            }
        }
    }

    public static String getChecksum(String numberId) {
        long sum = 0L;
        int i = numberId.length() - 1;

        for (int j = 0; i >= 0; ++j) {
            char digitChar = numberId.charAt(i);
            int digit = digitChar - 48;
            if ((j & 1) == 0) {
                sum += (long) (digit >= 5 ? (digit - 5) * 2 + 1 : digit * 2);
            } else {
                sum += (long) digit;
            }

            --i;
        }

        String sumStr = String.valueOf(sum * 9L);
        return sumStr.substring(sumStr.length() - 1, sumStr.length());
    }

    private static String getNumberId(long userId) {
        return String.valueOf(userId + 100000000000L);
    }

    public static long convertToDomainAccountId(long universalId) {
        if (!validateUniversalId(universalId)) {
            throw new IllegalArgumentException("统一账号id不合法：" + universalId);
        } else {
            String universalIdStr = String.valueOf(universalId);
            int length = universalIdStr.length();
            int type = Integer.valueOf(universalIdStr.substring(length - 2));
            if (type >= DomainAccountType.PASSPORT_AGENT.getValue()
                    && type <= DomainAccountType.PASSPORT_EUS.getValue()) {
                throw new IllegalArgumentException("不能转换passportId：" + universalId);
            } else {
                long domainAccountId =
                        Long.valueOf(universalIdStr.substring(0, length - 3)) - 100000000000L;
                if (DomainAccountType.FAMILY.getValue() == type && domainAccountId < 10000000L) {
                    domainAccountId += 10000000L;
                }

                return domainAccountId;
            }
        }
    }

    public static DomainAccountType convertToDomainAccountType(long universalId) {
        if (!validateUniversalId(universalId)) {
            throw new IllegalArgumentException("统一账号id不合法：" + universalId);
        } else {
            String universalIdStr = String.valueOf(universalId);
            int length = universalIdStr.length();
            int type = Integer.valueOf(universalIdStr.substring(length - 2));
            return DomainAccountType.fromValue(type);
        }
    }

    public static void main(String[] args) {
        long domainUserId = 71880L;
        DomainAccountType domainAccountType = DomainAccountType.COFFEE;
        generateUniversalAccountId(domainUserId, domainAccountType);

        long[] userIds = {52427L, 101273L, 332075L, 254034L};
        Arrays.stream(userIds)
                .map(UniversalAccountUtils::generateUniversalAccountId4XY)
                .forEach(System.out::println);
    }
}
