package com.lmm333.weixin.mp.service;

import com.lmm333.weixin.mp.dao.UserAnswerMapper;
import com.lmm333.weixin.mp.dao.UserMapper;
import com.lmm333.weixin.mp.model.Answer;
import com.lmm333.weixin.mp.model.Answer2;
import com.lmm333.weixin.mp.model.Enum;
import com.lmm333.weixin.mp.model.Question;
import com.lmm333.weixin.mp.model.Result;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.model.UserAnswer;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Service
class QAServiceImpl implements QAService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserAnswerMapper userAnswerMapper;

    private Map<Integer, Integer> answerQuestionMap = new HashMap<>();//<AnswerId, QuestionId>
    private Map<Integer, List<Integer>> questionAnswerMap = new HashMap<>();//<QuestionId, AnswerId>
    private Map<Integer, Integer> questionRightAnswerMap = new HashMap<>();//<QuestionId, QuestionId>


    public QAServiceImpl() {
        answerQuestionMap.put(11, 1);
        answerQuestionMap.put(12, 1);
        answerQuestionMap.put(13, 1);
        answerQuestionMap.put(14, 1);

        answerQuestionMap.put(21, 2);
        answerQuestionMap.put(22, 2);
        answerQuestionMap.put(23, 2);
        answerQuestionMap.put(24, 2);

        questionAnswerMap.put(1, Arrays.asList(11, 12, 13, 14));
        questionAnswerMap.put(2, Arrays.asList(21, 22, 23, 24));

        questionRightAnswerMap.put(1, 11);
        questionRightAnswerMap.put(2, 22);
    }

    @Override
    public void replaceUserRegisterType(User user) {
        userMapper.replaceUserRegisterTypeByWechatUserId(user);
    }

    @Override
    public Enum.InsertAnswerResultType insertUserAnswer(User user, UserAnswer userAnswer) {

        Enum.InsertAnswerResultType resultType = Enum.InsertAnswerResultType.Succeed;

        // Handle user
        User foundUser = userMapper.findByWechatUserId(user.getWechatUserId());
        if (foundUser == null) {
            try {
                userMapper.insert(user);
            } catch (Exception e) {
                return Enum.InsertAnswerResultType.Error;
            }

            foundUser = user;
        }

        if (foundUser.getRegisterType() == 0) {
            resultType = Enum.InsertAnswerResultType.SucceedNoUserInfo;
        }

        // Handle userAnswer
        boolean succeed = userAnswerMapper.replace(userAnswer);
        if (succeed) {
            return resultType;
        } else {
            return Enum.InsertAnswerResultType.Error;
        }
    }

    @Override
    public int findQuestionIdFromAnswerId(int questionId) {
        return answerQuestionMap.getOrDefault(questionId, -1);
    }

    @Override
    public Result findResultFromQuestionId(int questionId) {
        Result result = new Result();
        result.questionId = questionId;

        int rightAnswerId = questionRightAnswerMap.getOrDefault(questionId, -1);
        if (rightAnswerId == -1) {
            result.resultType = RESULE_INVALID_QUESTION_ID;
            return result;
        }
        result.rightAnswerId = rightAnswerId;

        List<Integer> answerList = questionAnswerMap.getOrDefault(questionId, null);
        if (answerList == null) {
            result.resultType = RESULE_INVALID_QUESTION_ID;
            return result;
        }


        // 没使用sql聚合函数，分多次(4次)查询结果，效率稍微差一点，但是应该也够了
        result.answerList = new ArrayList<>();
        for (int answerId : answerList) {
            Answer answerItem = new Answer();
            answerItem.answerId = answerId;
            answerItem.userCount = userAnswerMapper.findByUserAnswerIndex(answerId).size();
            result.answerList.add(answerItem);
        }

        // 用聚合函数，只要一次查询，但是count(*) 为 0的结果会被省略，需要自己处理0的结果，暂时不用
        //result.answerList = userAnswerMapper.findAnswerListByQuestionId(questionId);

        result.userList = userAnswerMapper.findUserByAnswerId(rightAnswerId);

        result.resultType = RESULE_SUCCEED;
        return result;
    }

    @Override
    public List<Question> getQA() {
        return Arrays.asList(
                new Question(
                        1,
                        "以下四人谁是新郎的最爱？",
                        "热身题",
                        "这是送命题，答错请双倍红包",
                        14,
                        Arrays.asList(
                                new Answer2(11, "费杨"),
                                new Answer2(12, "费虹"),
                                new Answer2(13, "费虹杨杨"),
                                new Answer2(14, "费杨虹虹"))
                ),

                new Question(
                        2,
                        "新郎从事IT行业，请问他具体的工作岗位是？",
                        "职业题",
                        "这是送命题，答错请双倍红包",
                        22,
                        Arrays.asList(
                                new Answer2(21, "设计狮"),
                                new Answer2(22, "程序猿"),
                                new Answer2(23, "产品狗"),
                                new Answer2(24, "测试喵"))
                ),

                new Question(
                        3,
                        "新娘从小热爱学习，2009年考入浙江大学本硕连读，2016年毕业开始工作，请问她的职业是？",
                        "职业题",
                        "整形美容科",
                        31,
                        Arrays.asList(
                                new Answer2(31, "医生"),
                                new Answer2(32, "律师"),
                                new Answer2(33, "公务员"),
                                new Answer2(34, "老师"))
                ),

                new Question(
                        4,
                        "新郎的父亲年轻的时候是个帅哥，退伍军人，请问他在哪里当兵？",
                        "父亲题",
                        "这是送命题，答错请双倍红包",
                        41,
                        Arrays.asList(
                                new Answer2(41, "吉林延吉"),
                                new Answer2(42, "福建厦门"),
                                new Answer2(43, "江苏连云港"),
                                new Answer2(44, "河南南阳"))
                ),

                new Question(
                        5,
                        "新郎的母亲勤勤恳恳工作30年，广受好评，今年4月光荣退休，请问她年轻时第一份工作在哪里？",
                        "母亲题",
                        "",
                        52,
                        Arrays.asList(
                                new Answer2(51, "果杂品公司"),
                                new Answer2(52, "糖烟酒公司"),
                                new Answer2(53, "烟草局"),
                                new Answer2(54, "供销社"))
                ),

                new Question(
                        6,
                        "新郎新娘是怎么认识的？",
                        "姻缘题",
                        "介绍人徐恒",
                        63,
                        Arrays.asList(
                                new Answer2(61, "青梅竹马"),
                                new Answer2(62, "广场相亲"),
                                new Answer2(63, "同学介绍"),
                                new Answer2(64, "旅行路上"))
                ),

                new Question(
                        7,
                        "新郎喜欢运动，经常参加各地马拉松，骑车，铁人三项，越野跑等比赛，他最长的一场比赛连续跑了多少公里？",
                        "爱好题",
                        "",
                        74,
                        Arrays.asList(
                                new Answer2(71, "21公里(半程马拉松)"),
                                new Answer2(72, "42.195公里(全程马拉松)"),
                                new Answer2(73, "50km(一百里)"),
                                new Answer2(74, "100km(一百公里)"))
                ),

                new Question(
                        8,
                        "新娘喜欢潜水，一个猛子扎下去最多潜了多深？",
                        "爱好题",
                        "",
                        82,
                        Arrays.asList(
                                new Answer2(81, "3米"),
                                new Answer2(82, "8米"),
                                new Answer2(83, "18米"),
                                new Answer2(84, "30米"))
                ),

                new Question(
                        9,
                        "新郎新娘喜欢旅行，东南亚13个国家已经去了10个了，请问下面哪个国家他们没去过~",
                        "旅行题",
                        "",
                        92,
                        Arrays.asList(
                                new Answer2(91, "越南"),
                                new Answer2(92, "老挝"),
                                new Answer2(93, "缅甸"),
                                new Answer2(94, "文莱"))
                ),

                new Question(
                        10,
                        "新郎新娘领证2018年6月6日在杭州市西湖区婚姻登记处领证结婚，请问这一天的节气是？",
                        "压轴题",
                        "",
                        102,
                        Arrays.asList(
                                new Answer2(101, "夏至"),
                                new Answer2(102, "芒种"),
                                new Answer2(103, "谷雨"),
                                new Answer2(104, "惊蛰"))
                ),

                new Question(
                        11,
                        "以下哪个最有可能是孩子的名字？",
                        "绝杀题",
                        "",
                        111,
                        Arrays.asList(
                                new Answer2(111, "娄永春"),
                                new Answer2(112, "娄夏眠"),
                                new Answer2(113, "娄知秋"),
                                new Answer2(114, "娄冬冬"))
                )
        );
    }
}
