package com.demo.springai.Controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Yupeng Li
 * @Date: 11/5/2024 13:12
 * @Description:
 */
@RestController
public class Multimodality {
    @Resource
    private ChatClient chatClient;


    /**
     * Multimodality
     * @param message message from user
     * @param imageUrl image URL from user
     * @return response message from AI
     * 根据用户输入的文本和图片生成回复：
     * http://localhost:8080/multimodality?msg=描述这个图片并且用英文翻译给我&&url=https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR9FUOcSsrbLPB9Sx5skzfzWuy-Cdjj6Wo0z4fWbxlyIqp7uN0SQdojaZY7dqTd97qPZQ&usqp=CAU
     */
    @RequestMapping("/multimodality")
    public Object multimodality(@RequestParam("msg") String message, @RequestParam("url") String imageUrl) {
        UserMessage userMessage = new UserMessage(message,
                List.of(new Media(MimeTypeUtils.IMAGE_PNG, imageUrl))
        );

        //基础调用方式
        //chatClient.call(userMessage);

        //通过Prompt的方式调用，可以指定模型，这里指定为GPT-4 Vision Preview
        ChatResponse response = chatClient.call(new Prompt(userMessage, OpenAiChatOptions.builder()
                .withModel(OpenAiApi.ChatModel.GPT_4_VISION_PREVIEW.getValue())
                .build())
        );

        // 返回AI的回复
        return response.getResult().getOutput().getContent();
    }

}
