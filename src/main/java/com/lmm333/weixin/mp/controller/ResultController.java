package com.lmm333.weixin.mp.controller;

import com.lmm333.weixin.mp.model.Question;
import com.lmm333.weixin.mp.model.Result;
import com.lmm333.weixin.mp.service.QAService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ResultController {
    @Autowired
    QAService qaService;

    @ResponseBody
    @RequestMapping(value = "result/{questionId}")
    public Result userAnswerRequest(@PathVariable int questionId) {
        return qaService.findResultFromQuestionId(questionId);
    }

    @RequestMapping("prize")
    public String prize() {
        return "prize";
    }

    @ResponseBody
    @RequestMapping("questions")
    public List<Question> getQA() {
        return qaService.getQA();
    }
}
