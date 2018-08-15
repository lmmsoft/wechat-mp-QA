package com.lmm333.weixin.mp.controller;

import com.lmm333.weixin.mp.model.Answer;
import com.lmm333.weixin.mp.model.Question;
import com.lmm333.weixin.mp.model.Result;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.service.QAService;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

            List<User> registeredUserList = new ArrayList<>();
            for (User user : result.userList) {
                if (!TextUtils.isEmpty(user.getNickname()) && !TextUtils.isEmpty(user.getHeadimgurl())) {
                    registeredUserList.add(user);
                }
            }


            String prize_user = "【没有有效用户】";
            String prize_image = "http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eo5FcvGCNgctPk4aYszHdAh3E5PAqt98E1YwA55qgUdPp8ohbjKY2BqnBbJedXqxh2yibSpGpDTmfA/132";

            if (registeredUserList.size() != 0) {
                int rand = new Random().nextInt(registeredUserList.size());
                prize_user = registeredUserList.get(rand).getNickname();
                prize_image = registeredUserList.get(rand).getHeadimgurl();
            }


            String userinfo;
            boolean hasRightAnswerUser = hasRightAnswerUser(result);
            if (hasRightAnswerUser) {
                userinfo = String.format("答对%d人, 他们是:", result.userList.size());
            } else {
                userinfo = String.format("没人答对, 所有答题的%d人都可以参与抽奖:", result.userList.size());
            }

            model.addAttribute("userinfo", userinfo);
            model.addAttribute("questionId", questionId - 1);// input 1 -> output 0
            model.addAttribute(result);
            model.addAttribute("q", questions);

            model.addAttribute("prize_id", questionId);
            model.addAttribute("prize_prize", qaService.getPrize().get(questionId - 1));
            model.addAttribute("prize_user", prize_user);
            model.addAttribute("prize_image", prize_image);

            return "question";
        } else if (questionId == 12) {
            //最多
            return "";
        } else {
            //统计页
            return "";
        }
    }

    private boolean hasRightAnswerUser(Result result) {
        for (Answer answer : result.answerList) {
            if (answer.answerId == result.rightAnswerId) {
                return answer.userCount != 0;
            }
        }
        return false;
    }

    @GetMapping("qapage")
    public String qa() {
        return "redirect:/qa.html";
    }
}
