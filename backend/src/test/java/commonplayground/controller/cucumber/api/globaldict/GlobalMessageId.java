package commonplayground.controller.cucumber.api.globaldict;

public class GlobalMessageId {
    private static String messageID;

    public static String getMessageID() {
        return messageID;
    }

    public static void setMessageID(String messageID) {
        GlobalMessageId.messageID = messageID;
    }
}
