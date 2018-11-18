package com.lmm333.weixin.mp.controller;

import com.lmm333.weixin.mp.model.Answer;
import com.lmm333.weixin.mp.model.Question;
import com.lmm333.weixin.mp.model.Result;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.model.UserRightAnswer;
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
import java.util.Collections;
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

            List<User> validUserList = findValidUsers(result.userList);

            String prize_user = "【没有有效用户】";
            String prize_image = "http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eo5FcvGCNgctPk4aYszHdAh3E5PAqt98E1YwA55qgUdPp8ohbjKY2BqnBbJedXqxh2yibSpGpDTmfA/132";

            if (validUserList.size() != 0) {
                int rand = new Random().nextInt(validUserList.size());
                prize_user = validUserList.get(rand).getNickname();
                prize_image = validUserList.get(rand).getHeadimgurl();
            }


            String userinfo;
            boolean hasRightAnswerUser = hasRightAnswerUser(result);
            if (hasRightAnswerUser) {
                userinfo = String.format("答对%d人, 他们是:", result.userList.size());
            } else {
                userinfo = String.format("没人答对, 所有参与答题的%d人都可以参与抽奖:", result.userList.size());
            }

            model.addAttribute("show_result_list", true);
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
            //page12 答对题目数量排名
            List<UserRightAnswer> result = qaService.findUserRightAnswerList();
            model.addAttribute("result", result);

            return "questionright";
        } else if (questionId == 101 || questionId == 102 || questionId == 103) {
            //显示所有杭州场报名用户
            model.addAttribute("show_result_list", false);

            List<User> validUserList = findHangzhouRegisterUsers(qaService.getAllUser());
            String prize_user = "【没有有效用户】";
            String prize_image = "http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eo5FcvGCNgctPk4aYszHdAh3E5PAqt98E1YwA55qgUdPp8ohbjKY2BqnBbJedXqxh2yibSpGpDTmfA/132";

            //乱序一下用户
            Collections.shuffle(validUserList);

            if (validUserList.size() != 0) {
                int rand = new Random().nextInt(validUserList.size());
                prize_user = validUserList.get(rand).getNickname();
                prize_image = validUserList.get(rand).getHeadimgurl();
            }

            Result result = new Result();
            result.userList = validUserList;
            model.addAttribute(result);

            String prize_prize = "";
            int prize_id = 11;
            switch (questionId) {
                case 101:
                    prize_prize = "Debo 汤锅";
                    break;
                case 102:
                    prize_prize = "九阳 电烤箱";
                    break;
                case 103:
                    prize_prize = "派德金 扫地机器人";
                    prize_id = 0;
                    break;
            }

            model.addAttribute("userinfo", String.format("一共有%d人成功报名", validUserList.size()));
            model.addAttribute("prize_id", prize_id);
            model.addAttribute("prize_prize", prize_prize);
            model.addAttribute("prize_user", prize_user);
            model.addAttribute("prize_image", prize_image);

            return "hangzhouluckydraw";
        } else {
            //page11 参与奖
            model.addAttribute("show_result_list", false);

            List<User> validUserList = findValidUsers(qaService.getAllUser());
            String prize_user = "【没有有效用户】";
            String prize_image = "http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eo5FcvGCNgctPk4aYszHdAh3E5PAqt98E1YwA55qgUdPp8ohbjKY2BqnBbJedXqxh2yibSpGpDTmfA/132";

            if (validUserList.size() != 0) {
                int rand = new Random().nextInt(validUserList.size());
                prize_user = validUserList.get(rand).getNickname();
                prize_image = validUserList.get(rand).getHeadimgurl();
            }

            Result result = new Result();
            result.userList = validUserList;
            model.addAttribute(result);

            model.addAttribute("userinfo", String.format("一共有%d人成功报名", validUserList.size()));
            model.addAttribute("prize_id", 11);
            model.addAttribute("prize_prize", qaService.getPrize().get(questionId - 1));
            model.addAttribute("prize_user", prize_user);
            model.addAttribute("prize_image", prize_image);

            return "question";
        }
    }

    private List<User> findValidUsers(List<User> userList) {
        List<User> validUsers = new ArrayList<>();
        for (User user : userList) {
            if (!TextUtils.isEmpty(user.getNickname()) && !TextUtils.isEmpty(user.getHeadimgurl())) {
                validUsers.add(user);
            }
        }
        return validUsers;
    }

    private List<User> findHangzhouRegisterUsers(List<User> userList) {
        List<User> validUsers = new ArrayList<>();
        for (User user : userList) {
            if (!TextUtils.isEmpty(user.getNickname())
                    && !TextUtils.isEmpty(user.getHeadimgurl())
                    && user.getRegisterType() == User.TYPE_WECHAT_OAUTHED_FOR_HANGZHOU_MARRAIGE) {
                validUsers.add(user);
            }
        }
        return validUsers;
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
