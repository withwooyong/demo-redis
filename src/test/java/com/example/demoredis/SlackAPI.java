package com.example.demoredis;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import java.io.IOException;

/**
 * https://zzang9ha.tistory.com/400
 * https://api.slack.com/apps/A06JX55AVFD?created=1
 * https://velog.io/@yujinaa/Slack-API-Client-Java%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4-Slack%EC%97%90-%EB%A9%94%EC%84%B8%EC%A7%80-%EB%B3%B4%EB%82%B4%EA%B8%B0
 */
public class SlackAPI {

    public static void main(String[] args) { //메인 메서드에서 호출위해 예외 처리
        try {
            messageToSlack();
        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
        }
    }
    //	private static Slack slack = Slack.getInstance();
    //	private static String token = "";
    //	private static MethodsClient methods = slack.methods(token);

    public static void messageToSlack() throws IOException, SlackApiException {
        Slack slack = Slack.getInstance();
        String token = "xoxb-3899646727600-6643313196978-Oek2wlJg2JfY1w0JKlClIeJo";
        MethodsClient methods = slack.methods(token);

        // Build a request object
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
//                .channel("#slack-bot-test1") // 채널명 or 채널 ID
                .channel("C06JX7N2LP4")
                .text(":smile: HAHA.")
                .build();

        // Get a response as a Java object
        ChatPostMessageResponse response = methods.chatPostMessage(request);
        System.out.println(response);
    }
}
