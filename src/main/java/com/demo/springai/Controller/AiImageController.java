package com.demo.springai.Controller;

import jakarta.annotation.Resource;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yupeng Li
 * @Date: 10/5/2024 16:56
 * @Description:
 */
@RestController
public class AiImageController {
    @Resource
    private OpenAiImageClient openAiImageClient;


    /**
     * Generate image based on the user input
     * @param message message from user
     * @return image URL
     */
    @RequestMapping("/image")
    public String image(@RequestParam("msg") String message){
        ImageResponse imageResponse = openAiImageClient.call(new ImagePrompt(message));
        System.out.println(imageResponse);

        // 获得图片的URL，点击链接查看图片
        String imageUrl = imageResponse.getResult().getOutput().getUrl();
        //TODO: 对图片进行处理

        return imageUrl;
    }
}
