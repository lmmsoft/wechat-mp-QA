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
import com.lmm333.weixin.mp.model.UserRightAnswer;

import org.springframework.dao.DuplicateKeyException;
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

    private List<String> prizeList = Arrays.asList(
            "迪士尼 米妮欢乐毯",
            "福玛特 专业杀菌除螨仪",
            "家用 卷蛋器",
            "尚品 营养被",
            "芳恩 贴心服装毯",
            "格兰仕 电饭煲",
            "KMW 欧式复底锅",
            "LOCK LOCK 保温水杯",
            "乐扣乐扣 保温饭盒",
            "高级 家用热水壶",
            "天然决明子 富硒磁疗能量枕",
            "福玛特-扫地机器人"
    );

    private List<Question> questionList = Arrays.asList(

            new Question(
                    1,
                    "职业题",
                    "新郎从事IT行业，请问他具体的工作岗位是？",
                    "大家现在看到的基于微信公众号的问答系统就是新郎最近用业余时间做的",
                    11,
                    Arrays.asList(
                            new Answer2(11, "手机程序开发"),
                            new Answer2(12, "网络游戏开发"),
                            new Answer2(13, "人工智能程序开发"),
                            new Answer2(14, "计算机维修"))
            ),

            new Question(
                    2,
                    "职业题",
                    "新娘从小成绩优异，2009年考入浙江大学，本硕连读，2016年毕业开始工作，请问她的职业是？",
                    "新娘现在是整形美容科的医生",
                    22,
                    Arrays.asList(
                            new Answer2(21, "律师"),
                            new Answer2(22, "医生"),
                            new Answer2(23, "老师"),
                            new Answer2(24, "公务员"))
            ),

            new Question(
                    3,
                    "父亲题",
                    "新郎的父亲年轻的时候做过边防战士，为祖国奉献过几年光阴，请问他当年是在哪里当的兵？",
                    "",
                    32,
                    Arrays.asList(
                            new Answer2(31, "福建厦门"),
                            new Answer2(32, "吉林延吉"),
                            new Answer2(33, "云南红河"),
                            new Answer2(34, "内蒙锡林郭勒"))
            ),

            new Question(
                    4,
                    "母亲题",
                    "新郎的母亲在会计的岗位上勤勤恳恳工作了30年，今年4月光荣退休，请问她年轻时第一个工作单位在哪里？",
                    "",
                    42,
                    Arrays.asList(
                            new Answer2(41, "果杂品公司"),
                            new Answer2(42, "糖烟酒公司"),
                            new Answer2(43, "烟草局"),
                            new Answer2(44, "供销社"))
            ),

            new Question(
                    5,
                    "姻缘题",
                    "新郎新娘是怎么认识的？",
                    "介绍人徐恒，是新郎的中学同学，新娘的大学同学",
                    53,
                    Arrays.asList(
                            new Answer2(51, "青梅竹马"),
                            new Answer2(52, "广场相亲"),
                            new Answer2(53, "同学介绍"),
                            new Answer2(54, "旅行路上"))
            ),

            new Question(
                    6,
                    "爱好题",
                    "新郎喜欢运动，经常参加各地的马拉松，骑车，铁人三项，越野跑等比赛，请问他参加过最长的一场跑步比赛距离有多长？",
                    "新郎2018年01月27日参加了香港100千米越野跑大赛，连续奔跑近24小时，翻越了几十座大山，克服了诸多困难，顺利到达终点，最后拿到小铜人的奖励~",
                    64,
                    Arrays.asList(
                            new Answer2(61, "半程马拉松(21公里)"),
                            new Answer2(62, "全程马拉松(42.195公里)"),
                            new Answer2(63, "百里(50公里)"),
                            new Answer2(64, "百公里(100公里)"))
            ),

            new Question(
                    7,
                    "爱好题",
                    "新娘喜欢潜水，最深能潜到水下多少米？",
                    "新郎新郎2016年在菲律宾长滩岛考到了OW潜水证，可以潜到18米，2017在泰国普吉岛考到了AOW潜水证，可以潜水30米",
                    74,
                    Arrays.asList(
                            new Answer2(71, "3米"),
                            new Answer2(72, "8米"),
                            new Answer2(73, "18米"),
                            new Answer2(74, "30米"))
            ),

            new Question(
                    8,
                    "旅行题",
                    "新郎新娘喜欢旅行，请问以下四个国家中哪个他们还没有去过？",
                    "新郎新娘喜欢旅行，东南亚13个国家已经去过10个了，",
                    82,
                    Arrays.asList(
                            new Answer2(81, "越南"),
                            new Answer2(82, "老挝"),
                            new Answer2(83, "缅甸"),
                            new Answer2(84, "文莱"))
            ),

            new Question(
                    9,
                    "压轴题",
                    "新郎新娘于2018年6月6日在杭州市西湖区婚姻登记处领证结婚，请问这一天的节气是？",
                    "芒种，播种的好季节，祝他们播下爱的种子\uD83D\uDC97收获满满的幸福~！",
                    92,
                    Arrays.asList(
                            new Answer2(91, "夏至"),
                            new Answer2(92, "芒种"),
                            new Answer2(93, "谷雨"),
                            new Answer2(94, "惊蛰"))
            ),

            new Question(
                    10,
                    "绝杀题",
                    "以下哪个最有可能是这对新人孩子的名字？",
                    "南京江浦娄氏家族自明朝天启年间从浙江余姚迁到南京，人丁兴旺，已历十六代，根据家谱，\"祖功宗德（正大光明），永邵丕基\"的排序，明明的爸爸是宗字辈，明明是德字辈，儿子将会是永字辈，所以最可能的名字是娄永春~",
                    111,
                    Arrays.asList(
                            new Answer2(101, "娄永春"),
                            new Answer2(102, "娄夏眠"),
                            new Answer2(103, "娄知秋"),
                            new Answer2(104, "娄冬冬"))
            )
    );
    private Map<Integer, Integer> answerQuestionMap = new HashMap<>();//<AnswerId, QuestionId>
    private Map<Integer, List<Integer>> questionAnswerMap = new HashMap<>();//<QuestionId, AnswerId-List>
    private Map<Integer, Integer> questionRightAnswerMap = new HashMap<>();//<QuestionId, AnswerId>


    public QAServiceImpl() {
        initQuestionList();
    }

    @Override
    public void replaceUserRegisterType(User user) {
        // 为了保存以前取关前取得的用户数据
        // step1：查询用户是否存在
        User foundUser = userMapper.findByWechatUserId(user.getWechatUserId());
        if (foundUser == null) {
            // 插入新用户
            userMapper.replaceUserRegisterTypeByWechatUserId(user);
        } else {
            // 更新用户状态
            foundUser.setRegisterType(user.getRegisterType());
            userMapper.updateUser(foundUser);
        }
    }

    @Override
    public void updateUserRegisterType(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public Enum.InsertAnswerResultType insertUserAnswer(User user, UserAnswer userAnswer) {

        Enum.InsertAnswerResultType resultType = Enum.InsertAnswerResultType.Succeed;

        // Handle user
        User foundUser = userMapper.findByWechatUserId(user.getWechatUserId());
        if (foundUser == null) {
            try {
                userMapper.updateUser(user);//TODO: 这里似乎不能插入新用户，要用insert或者replaceUserRegisterTypeByWechatUserId才行
            } catch (Exception e) {
                return Enum.InsertAnswerResultType.Error;
            }

            foundUser = user;
        }

        if (foundUser.getRegisterType() == 0) {
            resultType = Enum.InsertAnswerResultType.SucceedNoUserInfo;
        }

        // Check if right answer
        int rightAnswerId = questionRightAnswerMap.getOrDefault(userAnswer.getQuestionId(), -1);
        if (rightAnswerId == userAnswer.getUserAnswerIndex()) {
            userAnswer.setIsright(1);
        } else {
            userAnswer.setIsright(0);
        }

        // Handle userAnswer
        try {
            boolean succeed = userAnswerMapper.insert(userAnswer);
            if (succeed) {
                return resultType;
            } else {
                return Enum.InsertAnswerResultType.Error;
            }
        } catch (DuplicateKeyException e) {
            return Enum.InsertAnswerResultType.DuplicateQuestionId;
        } catch (Exception e) {
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

        if (result.userList.size() == 0) {
            result.userList = userAnswerMapper.findUserByQuestionId(questionId);
        }

        result.resultType = RESULE_SUCCEED;
        return result;
    }

    @Override
    public List<Question> getQA() {
        return questionList;
    }

    @Override
    public List<String> getPrize() {
        return prizeList;
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.findAll();
    }

    @Override
    public List<UserRightAnswer> findUserRightAnswerList() {
        return userAnswerMapper.findUserRightAnswerList();
    }

    private void initQuestionList() {
        for (Question question : questionList) {

            List<Integer> answerIdList = new ArrayList<>();
            for (Answer2 answer : question.getAnswerList()) {
                answerQuestionMap.put(answer.getAnswerId(), question.getQuestionId());
                answerIdList.add(answer.getAnswerId());
            }

            questionAnswerMap.put(question.getQuestionId(), answerIdList);

            questionRightAnswerMap.put(question.getQuestionId(), question.getRightAnswerIndex());
        }
    }
}
