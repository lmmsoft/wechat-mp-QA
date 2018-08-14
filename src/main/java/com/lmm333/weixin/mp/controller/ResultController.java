package com.lmm333.weixin.mp.controller;

import com.lmm333.weixin.mp.model.Question;
import com.lmm333.weixin.mp.model.Result;
import com.lmm333.weixin.mp.service.QAService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @RequestMapping("question/{questionId}")
    public String getQuestion(@PathVariable int questionId, Model model) {
        if (questionId >= 1 && questionId <= 10) {
            //题目答案
            Result result = qaService.findResultFromQuestionId(questionId);
            List<Question> questions = qaService.getQA();

            model.addAttribute("questionId", questionId - 1);// input 1 -> output 0
            model.addAttribute(result);
            model.addAttribute("q", questions);

            return "question";
        } else if (questionId == 12) {
            //最多
            return "";
        } else {
            //统计页
            return "";
        }
    }

    @GetMapping("qapage")
    public String qa() {
        return "redirect:/qa.html";
    }
}
