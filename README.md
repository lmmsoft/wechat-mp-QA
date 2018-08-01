### 本Demo基于Spring Boot构建，实现微信公众号开发功能。
### 本项目为weixin-java-tools的Demo演示程序，更多Demo请查阅：https://github.com/Wechat-Group/weixin-java-tools

[![Build Status](https://travis-ci.org/binarywang/weixin-java-mp-demo-springboot.svg?branch=master)](https://travis-ci.org/binarywang/weixin-java-mp-demo-springboot)
-----------------------

## 使用步骤：
1. 配置：复制`/src/main/resources/application.yml.template` 生成application.yml文件，根据自己需要填写相关配置（需要注意的是：yml文件内的属性冒号后面的文字之前需要加空格，可参考已有配置，否则属性会设置不成功）；	
1. 运行Java程序：`WxMpDemoApplication`；
1. 打开shell或cmd，进入ngrok目录，运行 `ngrok -config ngrok.cfg -subdomain my-domain 8080` 如果运行失败，请更换my-domain为其它字符串，直至连接成功；
1. 配置微信公众号中的接口地址：http://my-domain.tunnel.qydev.com/wechat/portal （注意my-domain要跟上面的一致，需要符合微信官方的要求）；
1. 根据自己需要修改各个handler的实现，加入自己的业务逻辑。

## ngrok
1. 非windows不能使用原ngrok.exe，下载[ngrok for mac](https://ngrok.com/download)
2. ./ngrok http 80 开启内网80端口的穿透，会返回一个临时网址，类似 于http://123456.ngrok.io 
3. 把网址 http://123456.ngrok.io/wechat/portal 填入微信公众号的后台
3. 更多ngrok信息请参阅[ngrok官方文档](https://ngrok.com/docs)
	
## 加解密的异常处理办法
1. 第一次使用，如果在加解密的过程中出现java.security.InvalidKeyException: Illegal key size，请参阅 [Link](https://github.com/Wechat-Group/weixin-java-tools/wiki/%E5%8A%A0%E8%A7%A3%E5%AF%86%E7%9A%84%E5%BC%82%E5%B8%B8%E5%A4%84%E7%90%86%E5%8A%9E%E6%B3%95)

## 数据持久化
1. springboot redis [doc](https://docs.spring.io/spring-data/redis/docs/current/reference/html/#get-started)

## redis in docker
1. docker 安装：
```
    docker pull redis # 下载redis镜像
    docker run -p 6379:6379 --name redis redis:latest # 运行容器， 将容器的6379端口映射到主机的6379端口
    docker ps # 列出容器列表
    docker exec -it 43f7a65ec7f8 redis-cli # 运行redis-cli
    docker exec -it 81 redis-benchmark -n 100000 -q # 同时执行10万个请求测试性能
```

## mysql in docker
1. docker 安装：
    ```
    docker search mysql
    docker pull mysql
    docker run -p 3306:3306 --name mymysql -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.6
    docker run -p 3306:3306 --name mymysql -v /opt/docker_v/mysql/conf:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=123456 -d imageID
    docker run -p 3306:3306 --name mymysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:latest --default-authentication-plugin=mysql_native_password
    docker run -p 3406:3306 --name mymysql -e MYSQL_ROOT_PASSWORD=mypassword -d mysql
    ```

2. [Link to Docker Hub](https://hub.docker.com/_/mysql/)
2. Meet error: Unable to load authentication plugin 'caching_sha2_password', then use
    ```
    docker exec -it CONTAINER_ID bash
    mysql --user=root --password=123456    
    ALTER USER root@localhost IDENTIFIED WITH mysql_native_password BY '123456';
    ```
3. 还是不行，改安装mysql 5.7
    ```
    $ docker pull mysql:5.7
    $ docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=secret -d mysql:5.7
    
    ```

接收微信请求：[signature=[0382770341aee8d8e79abc3d02768767a2b779c7], encType=[aes], msgSignature=[1b39e6edb229216efd120418b7e33e2a979acbdc], timestamp=[1532838523], nonce=[258257664], requestBody=[
<xml>
    <ToUserName><![CDATA[gh_ac6bbe3aac55]]></ToUserName>
    <FromUserName><![CDATA[ok3SF1s4vWK48-1aM3b4p9gMq3Bs]]></FromUserName>
    <CreateTime>1532838523</CreateTime>
    <MsgType><![CDATA[text]]></MsgType>
    <Content><![CDATA[/::P]]></Content>
    <MsgId>6583491326766490164</MsgId>
    <Encrypt><![CDATA[fJLIlJYWRHwvStvfHgcBeYxE14wc0flWomWEM9C2sy1ETuxjUL3KqGalGmIzGssaK3zPh9FCL45h5r/AUSBDoMNh0CTncGH9HUjkDOa7AuwnVdq/eR3toxXElLlphP8lORCYa4n45fxCXICIlAWaZE7MlMbZFp8VqKyMilm1P5px/j2K7oFnhrM/EZWwsPSmi4kS5sgXlI+GSId4rKSyQE/1tGGqC559fMMbut6Xr8SRmuVRcXroiA8bSahccEX/1WbagZJ7RMWh/6f0ZYtc0htI6JfeRaqY35imj8DPHXovQtIslRxshQheYtpDmlYW27eGPxp0kuyN8xGmDum78r3duYG3EZ1MtSGMkg7s4oKyrK4pKmRjf4OMbJEE0hfjXrZYomCZovPZUW2TnS36mpQIKXpnpJEJkTVUmZnST0Q=]]></Encrypt>
</xml>

] 

消息解密后内容为：
WxMpXmlMessage[
  toUser=gh_ac6bbe3aac55
  fromUser=ok3SF1s4vWK48-1aM3b4p9gMq3Bs
  createTime=1532838523
  msgType=text
  content=/::P
  msgId=6583491326766490164
  scanCodeInfo=ScanCodeInfo[<all null values>]
  sendPicsInfo=SendPicsInfo[
  picList=[]
]
  sendLocationInfo=SendLocationInfo[<all null values>]
  hardWare=HardWare[<all null values>]
] 



接收微信请求：[signature=[a303166cb250ee206980344407c8a0360ec007a7], encType=[aes], msgSignature=[7f487d61b02881c9eecff9db351d7e61234f5d36], timestamp=[1532839558], nonce=[1284810716], requestBody=[
<xml>
    <ToUserName><![CDATA[gh_ac6bbe3aac55]]></ToUserName>
    <FromUserName><![CDATA[ok3SF1s4vWK48-1aM3b4p9gMq3Bs]]></FromUserName>
    <CreateTime>1532839558</CreateTime>
    <MsgType><![CDATA[text]]></MsgType>
    <Content><![CDATA[天天开心]]></Content>
    <MsgId>6583495772057641788</MsgId>
    <Encrypt><![CDATA[Ihoq4avH25yArOp+SIMfnpx+s4mKAV6hY2FcdL+48qLKmak3VL1cJxvnUqW5ir73T+hSBvERrDfwx2QlXRegvpQYRpTse5811dfQoKj5WmIcwlvknoy4XmJtDa0GFvqrZrRtlIAUFEBJd2Oc0p8nL/Q1B1fWx8yLIVnP+pMZPs/YX0qto0q3z4Cvnt7i6jZhJUJnZnTfLiFysg+9wRkM5HLFB04O4kxlayeVa8DxPZLN028lxdFk66f99EL191yyERW7g0XATDuNhkxpNzOQGI4c9rtzC28qIHnu5EsGzOPeGXM+DwzzM9MB3RCohGzFeekn83U2sB6+9vdAN5O6PW1tyt4e+ptWzFDv8209RlO9wJ+NW9Hm1boAVW4AQXVhFRlY4Es/DixuaZ49IxlNqQtsT/1XZLrFGLlssntWMKIzX9CAzHMIV87vAYXcIwtKX9sSpTYnSfl/dHyTvJ53yQ==]]></Encrypt>
</xml>

] 
2018-07-29 12:45:59.292 DEBUG 5415 --- [nio-8080-exec-2] c.g.b.d.w.m.controller.WechatController  : 
消息解密后内容为：
WxMpXmlMessage[
  toUser=gh_ac6bbe3aac55
  fromUser=ok3SF1s4vWK48-1aM3b4p9gMq3Bs
  createTime=1532839558
  msgType=text
  content=天天开心
  msgId=6583495772057641788
  scanCodeInfo=ScanCodeInfo[<all null values>]
  sendPicsInfo=SendPicsInfo[
  picList=[]
]
  sendLocationInfo=SendLocationInfo[<all null values>]
  hardWare=HardWare[<all null values>]
] 
2018-07-29 12:45:59.296  INFO 5415 --- [pool-1-thread-7] c.g.b.demo.wx.mp.handler.LogHandler      : 
接收到请求消息，内容：{"toUser":"gh_ac6bbe3aac55","fromUser":"ok3SF1s4vWK48-1aM3b4p9gMq3Bs","createTime":1532839558,"msgType":"text","content":"天天开心","msgId":6583495772057641788,"scanCodeInfo":{},"sendPicsInfo":{"picList":[]},"sendLocationInfo":{},"hardWare":{}}
2018-07-29 12:45:59.298 DEBUG 5415 --- [nio-8080-exec-2] c.g.b.d.w.m.controller.WechatController  : 
组装回复信息：<xml>
<Encrypt><![CDATA[xtX4x8O4YpzDpgio5MA9KoDVUSCzt1GIgVzOkrhRpy5yKDLNEsExctqto2sM46BvlgloavJnh+pqj2LNd7w1+EvjHsBDbINTOwpN+3U3vEqIrwZPHMJ7MFNkuksD5YgBA6Bkc6lRbZk/fXwN8fwyFbkq1WfxSGFDi+YuudyyQA369qmQoq2pP8ht9eqxWivWG8m3w7hq0R+cJ0tX5DhTxzGhku00Dad/FvMFSmrrYBL0FfLdmYQgmxsYOX4FpaTLM3ElDmMOIQ1VHdrNThiTRZBRjnPxhXs1jfAfi2O41MLPqHupKZkplwL/6mML6HZNPFdPgkd9Jjh6f5xeFgD18+V0U5udkkJSrsWRi47Ouy4rZE7dKjskpfoCIbdq1iVwccBgv5QOHUTVCP9t0mWnSFqKC8RJm4LkIwFTe5Pti/ZbOkb2S/DQjbUIckFVg6FQB9SOwKRFJkKIw8psGpN+6C1LOQ2yRJRbJWOTpKpwKAmF5RLGw1eI50p1m33ysl1SD3gY8ndqsia/mVlcqayXNwWdQZRfyKyokdbeSR+7Gygv4jIPSjbQMr9SG1itDbf2Wa4X0CZYvJZt5CznFkMJzrFdgFsgMsmrwEk6tlTHPcDaP72KKmUHUw/zjSHvxj6C1toNHE3ImcjL3BHxVyOnwvJ29lonpXM+BOcp6+nUCAcYkPZCrORGwkQY5YNyTkUd9OQCzbZ5k0/bSwmUsvqXa+SW45dmUoJBZVBxv3kDnwz8iOcw9bm/aRcVvUQERNUO]]></Encrypt>
<MsgSignature><![CDATA[aa5beb03ecdf4f60de296c297a60b07d549113be]]></MsgSignature>
<TimeStamp>1532839559</TimeStamp>
<Nonce><![CDATA[v9A0CbsH4oPsdF10]]></Nonce>
</xml>
2018-07-29 12:45:59.300 DEBUG 5415 --- [nio-8080-exec-2] m.m.a.RequestResponseBodyMethodProcessor : Written [<xml>
<Encrypt><![CDATA[xtX4x8O4YpzDpgio5MA9KoDVUSCzt1GIgVzOkrhRpy5yKDLNEsExctqto2sM46BvlgloavJnh+pqj2LNd7w1+EvjHsBDbINTOwpN+3U3vEqIrwZPHMJ7MFNkuksD5YgBA6Bkc6lRbZk/fXwN8fwyFbkq1WfxSGFDi+YuudyyQA369qmQoq2pP8ht9eqxWivWG8m3w7hq0R+cJ0tX5DhTxzGhku00Dad/FvMFSmrrYBL0FfLdmYQgmxsYOX4FpaTLM3ElDmMOIQ1VHdrNThiTRZBRjnPxhXs1jfAfi2O41MLPqHupKZkplwL/6mML6HZNPFdPgkd9Jjh6f5xeFgD18+V0U5udkkJSrsWRi47Ouy4rZE7dKjskpfoCIbdq1iVwccBgv5QOHUTVCP9t0mWnSFqKC8RJm4LkIwFTe5Pti/ZbOkb2S/DQjbUIckFVg6FQB9SOwKRFJkKIw8psGpN+6C1LOQ2yRJRbJWOTpKpwKAmF5RLGw1eI50p1m33ysl1SD3gY8ndqsia/mVlcqayXNwWdQZRfyKyokdbeSR+7Gygv4jIPSjbQMr9SG1itDbf2Wa4X0CZYvJZt5CznFkMJzrFdgFsgMsmrwEk6tlTHPcDaP72KKmUHUw/zjSHvxj6C1toNHE3ImcjL3BHxVyOnwvJ29lonpXM+BOcp6+nUCAcYkPZCrORGwkQY5YNyTkUd9OQCzbZ5k0/bSwmUsvqXa+SW45dmUoJBZVBxv3kDnwz8iOcw9bm/aRcVvUQERNUO]]></Encrypt>
<MsgSignature><![CDATA[aa5beb03ecdf4f60de296c297a60b07d549113be]]></MsgSignature>
<TimeStamp>1532839559</TimeStamp>
<Nonce><![CDATA[v9A0CbsH4oPsdF10]]></Nonce>
</xml>] as "application/xml;charset=UTF-8" using [org.springframework.http.converter.StringHttpMessageConverter@18a645fd]
2018-07-29 12:45:59.300 DEBUG 5415 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : Null ModelAndView returned to DispatcherServlet with name 'dispatcherServlet': assuming HandlerAdapter completed request handling
2018-07-29 12:45:59.300 DEBUG 5415 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : Successfully completed request



//图片请求

接收微信请求：[signature=[2de424b864d676d87413fc3612a6deb27ec3c698], encType=[aes], msgSignature=[874d50d4459cf0b0741e3bc734a02b8bec766650], timestamp=[1532839645], nonce=[969889311], requestBody=[
<xml>
    <ToUserName><![CDATA[gh_ac6bbe3aac55]]></ToUserName>
    <FromUserName><![CDATA[ok3SF1s4vWK48-1aM3b4p9gMq3Bs]]></FromUserName>
    <CreateTime>1532839644</CreateTime>
    <MsgType><![CDATA[image]]></MsgType>
    <PicUrl><![CDATA[http://mmbiz.qpic.cn/mmbiz_jpg/BtcSiaAbRvkUiagyCtEZ2zPickiciaF8JHrzo3EGbBibQgbHARh46lV8ekQRickTM3hQBmRPWiaibBicM1Y934hHoic8F7LjQ/0]]></PicUrl>
    <MsgId>6583496141424829266</MsgId>
    <MediaId><![CDATA[MvvkxjFMY2w14eREEIUhGXBaQlpGrCs6cPXvOh9G-5W524Yb9flKID90j062n38Z]]></MediaId>
    <Encrypt><![CDATA[yh8cmqvEMCAGObsGeEUJu6bmK9gCdcjXyXhO0EPy0l3VZ/QvNkpPzsNIZ4za/7kaCFRleY8Zn10ElVMaxatqP0GhUIkBporGQ9P5aGsuJzTfGe6E9Lwu7Jd08On+a4lcNSqJjtqcK5TdYUM9Dctgb3/Uh2OpaTrsBu5WStylQjt7GKAdAkyTUo3OMKenfyezvYxdpfQeddbDakwh/8W67+CNEXalUBl/PoDcWdyDAfz19Hnxa2qpSjBJPasKXZR0CuDNvrsS1Gj8f3o9Y8ZupbC1TJ05ggUY+SD2VSZQ8A2U0d0jkyUdqiafA3H8L0mho6wrRXmHoK8R9gujr0/HGGAqMRqAGJhTY6DCy1zRbUzWNcH0r0Ah+cMI2ONPDRMz1En+JG4lBWmo5H7m/2O5d9wYADvI0h1JAltinA0STNVXgzSsaLSYOhIaMORjeAq5OxnFFGc1VfitxtqxD8mqB/yVBA81azcFJdSnasQT27hGUdf6USDQPt9W4iAzzPplAOR7tg3gI8Tok28ux4OlLkT/oYV6YU8QrGgG6Az5XsZRSSngxLKggEC+scIa/wpZU2rADAs3EcUEEHIIxty22+/FnCr3K0sE/DrdCRerI1B/vTTY+BBSOZcbWeS/Gpcfy2n6+Z4A+Shd5DTozoL7SUvHVYEwvPWwJQ2cBR5ZJskBsPGjeul8U1kOqlYwzIxRpdgLH6w8tnVWcQhYCwnFkQ==]]></Encrypt>
</xml>

] 
2018-07-29 12:47:26.714 DEBUG 5415 --- [nio-8080-exec-4] c.g.b.d.w.m.controller.WechatController  : 
消息解密后内容为：
WxMpXmlMessage[
  toUser=gh_ac6bbe3aac55
  fromUser=ok3SF1s4vWK48-1aM3b4p9gMq3Bs
  createTime=1532839644
  msgType=image
  msgId=6583496141424829266
  picUrl=http://mmbiz.qpic.cn/mmbiz_jpg/BtcSiaAbRvkUiagyCtEZ2zPickiciaF8JHrzo3EGbBibQgbHARh46lV8ekQRickTM3hQBmRPWiaibBicM1Y934hHoic8F7LjQ/0
  mediaId=MvvkxjFMY2w14eREEIUhGXBaQlpGrCs6cPXvOh9G-5W524Yb9flKID90j062n38Z
  scanCodeInfo=ScanCodeInfo[<all null values>]
  sendPicsInfo=SendPicsInfo[
  picList=[]
]
  sendLocationInfo=SendLocationInfo[<all null values>]
  hardWare=HardWare[<all null values>]
] 
2018-07-29 12:47:26.716  INFO 5415 --- [pool-1-thread-9] c.g.b.demo.wx.mp.handler.LogHandler      : 
接收到请求消息，内容：{"toUser":"gh_ac6bbe3aac55","fromUser":"ok3SF1s4vWK48-1aM3b4p9gMq3Bs","createTime":1532839644,"msgType":"image","msgId":6583496141424829266,"picUrl":"http://mmbiz.qpic.cn/mmbiz_jpg/BtcSiaAbRvkUiagyCtEZ2zPickiciaF8JHrzo3EGbBibQgbHARh46lV8ekQRickTM3hQBmRPWiaibBicM1Y934hHoic8F7LjQ/0","mediaId":"MvvkxjFMY2w14eREEIUhGXBaQlpGrCs6cPXvOh9G-5W524Yb9flKID90j062n38Z","scanCodeInfo":{},"sendPicsInfo":{"picList":[]},"sendLocationInfo":{},"hardWare":{}}
2018-07-29 12:47:26.717 DEBUG 5415 --- [nio-8080-exec-4] c.g.b.d.w.m.controller.WechatController  : 
组装回复信息：<xml>
<Encrypt><![CDATA[hHLIS00AePBFsjWZ/dF1GCo4gHrV/CjMrwopLaa5/DnElV9sYTsmkOStAolDvyxLGGWeFFLHSxDxCCrh3bYwSUbVVPAD/OcfSLQ47vCsELJOkBaY//r5QcvzdJNYUT2RoJPN1CxggCeKERyIh14Q4P0mk7OfyfKKZtf16CYedXE3ZXy7SjgHZA3jBqv2Py9XnU1b/GOAs73WMMxdu3rjX63hmnPpyLba8gZ8HKeOeSlQOFQ758st/CGOkDL87pl0X7tWMTDu8+c4gdAU3n+C1BAaZIA0Af3C8BYLT5bEme590B4Z2dkuuuGgO01yiAlk1Vs2cyH1yQ3/lqtPpTYHrUeAILD2T1Vx0pAEs/0T1F88mVQYT2k9bik/VAxJSq0eVwjSoWbMYQXFpfXLNLimDRNdLDzxXz3Pj71cwKT20SbvpvCFM9r3pgTGJ3r6TrvMsy1GYNcTD1EWAfKnA620VCqPQpg18lXOfvMq2VYGRmZjkPAFeBUvkIm/9Ir/UtaPVLMj+5okhiqSkEzEftP4ysAPVRiXl1CyDYm8g6qQE0UDIrgUnkPBc+tsYFrbc/LdNU0zx58tiZgsBVH4OCPYhG3sbGU3ncFQ6WzNfaLKPfOamPwPhWPSoS675iABW/mugyEpRHOHl37ZALLqd8rUsVi2COvWavbk/7NILCeQwtfuAydQgoaKmiLBFta+SRa7BYmIUmucTBbQ2vfqjPP6Gwb8YVt9pbKLbvchVz7LY07Q6H0KziXP85xAPwITDsaS3oo/WeynChJg6JCS28kQPJaMGnSH+6XTA++rBVlYgBHNEu7mdptqlC6BC9EmsTdJ5i/Ph+sSXo+yBTF36glnGS1UM0X7c7YkrfdUjyeCsGIkAP65251h5ShIliBLHoVFi/z4naYzprDARMKFiaV/x57T0+eoERBymXceUmqJ7dIWmoTEuIzgLxgPfXGQKA74s6bEhU6N2NJRzzdVkmJSL1zJ21r700GhDxQqC0l6q2hUme3DB+8whiS0dQrgfNeH]]></Encrypt>
<MsgSignature><![CDATA[f3b052203ea5624e00c3997daea07b38df79a0db]]></MsgSignature>
<TimeStamp>1532839646</TimeStamp>
<Nonce><![CDATA[5zfWgFeaYAysAFBS]]></Nonce>
</xml>
2018-07-29 12:47:26.718 DEBUG 5415 --- [nio-8080-exec-4] m.m.a.RequestResponseBodyMethodProcessor : Written [<xml>
<Encrypt><![CDATA[hHLIS00AePBFsjWZ/dF1GCo4gHrV/CjMrwopLaa5/DnElV9sYTsmkOStAolDvyxLGGWeFFLHSxDxCCrh3bYwSUbVVPAD/OcfSLQ47vCsELJOkBaY//r5QcvzdJNYUT2RoJPN1CxggCeKERyIh14Q4P0mk7OfyfKKZtf16CYedXE3ZXy7SjgHZA3jBqv2Py9XnU1b/GOAs73WMMxdu3rjX63hmnPpyLba8gZ8HKeOeSlQOFQ758st/CGOkDL87pl0X7tWMTDu8+c4gdAU3n+C1BAaZIA0Af3C8BYLT5bEme590B4Z2dkuuuGgO01yiAlk1Vs2cyH1yQ3/lqtPpTYHrUeAILD2T1Vx0pAEs/0T1F88mVQYT2k9bik/VAxJSq0eVwjSoWbMYQXFpfXLNLimDRNdLDzxXz3Pj71cwKT20SbvpvCFM9r3pgTGJ3r6TrvMsy1GYNcTD1EWAfKnA620VCqPQpg18lXOfvMq2VYGRmZjkPAFeBUvkIm/9Ir/UtaPVLMj+5okhiqSkEzEftP4ysAPVRiXl1CyDYm8g6qQE0UDIrgUnkPBc+tsYFrbc/LdNU0zx58tiZgsBVH4OCPYhG3sbGU3ncFQ6WzNfaLKPfOamPwPhWPSoS675iABW/mugyEpRHOHl37ZALLqd8rUsVi2COvWavbk/7NILCeQwtfuAydQgoaKmiLBFta+SRa7BYmIUmucTBbQ2vfqjPP6Gwb8YVt9pbKLbvchVz7LY07Q6H0KziXP85xAPwITDsaS3oo/WeynChJg6JCS28kQPJaMGnSH+6XTA++rBVlYgBHNEu7mdptqlC6BC9EmsTdJ5i/Ph+sSXo+yBTF36glnGS1UM0X7c7YkrfdUjyeCsGIkAP65251h5ShIliBLHoVFi/z4naYzprDARMKFiaV/x57T0+eoERBymXceUmqJ7dIWmoTEuIzgLxgPfXGQKA74s6bEhU6N2NJRzzdVkmJSL1zJ21r700GhDxQqC0l6q2hUme3DB+8whiS0dQrgfNeH]]></Encrypt>
<MsgSignature><![CDATA[f3b052203ea5624e00c3997daea07b38df79a0db]]></MsgSignature>
<TimeStamp>1532839646</TimeStamp>
<Nonce><![CDATA[5zfWgFeaYAysAFBS]]></Nonce>
</xml>] as "application/xml;charset=UTF-8" using [org.springframework.http.converter.StringHttpMessageConverter@18a645fd]
2018-07-29 12:47:26.718 DEBUG 5415 --- [nio-8080-exec-4] o.s.web.servlet.DispatcherServlet        : Null ModelAndView returned to DispatcherServlet with name 'dispatcherServlet': assuming HandlerAdapter completed request handling
2018-07-29 12:47:26.718 DEBUG 5415 --- [nio-8080-exec-4] o.s.web.servlet.DispatcherServlet        : Successfully completed request
