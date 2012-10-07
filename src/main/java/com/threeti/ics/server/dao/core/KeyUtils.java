package com.threeti.ics.server.dao.core;

/**
 * Created by IntelliJ IDEA.
 * User: johnson
 * Date: 9/16/12
 * Time: 1:20 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class KeyUtils {
    public static String mobileAppKey(final String appKey) {
        return String.format("mobileApp:%s:id", appKey);
    }

    public static String mobileDeviceUid(final String uid) {
        return String.format("mobilDevice:%s:id", uid);
    }

    public static String customerServiceUserUserName(final String userName) {
        return String.format("customerServiceUser:%s:id", userName);
    }

    public static String topicProductId(final String productId) {
        return String.format("conversationTopic:%s:id", productId);
    }

    public static String serviceTokenOfToken(String token) {
        return String.format("serviceToken:%s:id", token);
    }

    public static String conversationProductIdAndVisitorToken(final String productId, final String visitorServiceToken) {
        return String.format("conversation:topic:productId:%s:visitor:%s:id", productId, visitorServiceToken);
    }

    public static String conversationProductIdAndVisitorKey(final String visitor) {
        return String.format("conversation:topic:productId:*:visitor:%s:id", visitor);
    }

    public static String conversationMessage(final Long conversationId) {
        return String.format("conversation:%s:messages", conversationId);
    }

    public static String conversationMessageIdKey(final String conversationId, final String messageId) {
        return String.format("conversation:%s:message:%s", conversationId, messageId);
    }

    public static String conversationTopic(final String topicId) {
        return String.format("conversationTopic:%s", topicId);
    }

    public static String conversationVisitor(final String visitor) {
        return String.format("conversation:%s", visitor);
    }

    public static String textTemplateAppKeyAndGroupKey(final String appKey, final String group) {
        return String.format("messageTemplate:appKey:%s:group:%s", appKey, group);
    }

    public static String textTemplateAppKeyAndGroupKeyPrefix(final String appKey) {
        return String.format("messageTemplate:appKey:%s:group:*", appKey);
    }

}
