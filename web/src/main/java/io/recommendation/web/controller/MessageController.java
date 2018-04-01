package io.recommendation.web.controller;

import io.recommendation.common.vo.ResponseVo;
import io.recommendation.web.producer.KafkaSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController{

    private KafkaSender kafkaSender = new KafkaSender();

    @GetMapping("/execute")
    public ResponseVo sendMessage(){
        kafkaSender.sendMessage("message","/root/recommend.sh");

        return ResponseVo.ok();
    }
}
