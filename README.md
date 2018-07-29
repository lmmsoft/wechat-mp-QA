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
2. ./ngrok http 80 开启内网80端口的穿透，会返回一个临时网址，类似 于http://123456.ngrok.io ，填入微信公众号的后台
3. [ngrok官方文档](https://ngrok.com/docs)
	
