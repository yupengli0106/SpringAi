package com.demo.springai.Controller;

import jakarta.annotation.Resource;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yupeng Li
 * @Date: 10/5/2024 02:02
 * @Description:
 */
@RestController
public class AiChatController {
    @Resource
    private OpenAiChatClient openAiChatClient;


    /**
     * Chat with AI
     * @param message message from user
     * @return response message from AI
     * we can send a request to http://localhost:8080/chat?msg=hello
     * then we will get a response from AI
     */
    @RequestMapping("/chat")
    public String chat(@RequestParam("msg") String message) {
        String response = openAiChatClient.call(message);
        return response;
    }

}
