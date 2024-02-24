package com.example.demoredis.util;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
@RequiredArgsConstructor
public class SlackUtil {

    @Value(value = "${slack.token}")
    private String token;

    @Value(value = "${slack.channel}")
    private String channel;

    public void send(String message) {
        try {
            MethodsClient methods = Slack.getInstance().methods(token);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channel)
                    .text(message)
                    .build();
            methods.chatPostMessage(request);
            log.info("success:{}", message);
        } catch (SlackApiException | IOException e) {
            log.error(e.getMessage());
        }
    }
}
