## 基于Spring Boot构建，实现微信公众号开发功能。
- 本项目基于weixin-java-tools的Demo演示程序迭代改写
- 原始SDK地址： https://github.com/Wechat-Group/weixin-java-tools
- 支持单公众号demo 地址 https://github.com/binarywang/weixin-java-mp-demo-springboot
- 支持多公众号demo 地址 https://github.com/binarywang/weixin-java-mp-multi-demo
- 开放平台Demo 地址 https://github.com/Wechat-Group/weixin-java-open-demo
- 更多Demo列表：https://github.com/Wechat-Group/weixin-java-tools/blob/master/demo.md

-----------------------

## 使用步骤：
1. 配置：复制`/src/main/resources/application.yml.template` 生成application.yml文件，根据自己需要填写相关配置（需要注意的是：yml文件内的属性冒号后面的文字之前需要加空格，可参考已有配置，否则属性会设置不成功）；	
1. 运行Java程序：`WxMpDemoApplication`；
1. 打开shell或cmd，进入ngrok目录，运行 `ngrok -config ngrok.cfg -subdomain my-domain 8080` 如果运行失败，请更换my-domain为其它字符串，直至连接成功；
1. 配置微信公众号中的接口地址：http://my-domain.tunnel.qydev.com/wechat/portal （注意my-domain要跟上面的一致，需要符合微信官方的要求）；
1. 根据自己需要修改各个handler的实现，加入自己的业务逻辑。

## ngrok(内网穿透代理，用于把微信公众号的请求转发到本地环境，方便调试)
1. 非windows不能使用原ngrok.exe，下载[ngrok for mac](https://ngrok.com/download)
2. ./ngrok http 80 开启内网80端口的穿透，会返回一个临时网址，类似 于http://123456.ngrok.io 
3. 把网址 http://123456.ngrok.io/wechat/portal 填入微信公众号的后台
3. 更多ngrok信息请参阅[ngrok官方文档](https://ngrok.com/docs)
	
## 加解密的异常处理办法
1. 第一次使用，如果在加解密的过程中出现java.security.InvalidKeyException: Illegal key size，请参阅 [Link](https://github.com/Wechat-Group/weixin-java-tools/wiki/%E5%8A%A0%E8%A7%A3%E5%AF%86%E7%9A%84%E5%BC%82%E5%B8%B8%E5%A4%84%E7%90%86%E5%8A%9E%E6%B3%95)

## 数据持久化
### 1. redis (已弃用)
1. springboot redis [doc](https://docs.spring.io/spring-data/redis/docs/current/reference/html/#get-started)
1. redis in docker 安装：
    ```
    docker pull redis # 下载redis镜像
    docker run -p 6379:6379 --name redis redis:latest # 运行容器， 将容器的6379端口映射到主机的6379端口
    docker ps # 列出容器列表
    docker exec -it 43f7a65ec7f8 redis-cli # 运行redis-cli
    docker exec -it 81 redis-benchmark -n 100000 -q # 同时执行10万个请求测试性能
    ```

### 2. MySQL
3. mysql in docker安装：
    ```
    docker search mysql
    docker pull mysql
    docker run -p 3306:3306 --name mymysql -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.6
    docker run -p 3306:3306 --name mymysql -v /opt/docker_v/mysql/conf:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=123456 -d imageID
    docker run -p 3306:3306 --name mymysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:latest --default-authentication-plugin=mysql_native_password
    docker run -p 3406:3306 --name mymysql -e MYSQL_ROOT_PASSWORD=mypassword -d mysql
    ```

2. [Link to MySQL Docker Hub](https://hub.docker.com/_/mysql/)
2. Meet error: Unable to load authentication plugin 'caching_sha2_password', then use
    ```
    docker exec -it CONTAINER_ID bash
    mysql --user=root --password=123456    
    ALTER USER root@localhost IDENTIFIED WITH mysql_native_password BY '123456';
    ```
3. MySQL8遇到密码安全性问题，改安装mysql 5.7
    ```
    $ docker pull mysql:5.7
    $ docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=secret -d mysql:5.7
    ```
1. MySQL emoji存储问题，数据库编码设置utf8mb4，修改连接字符串，详见相关commit

## 部署上线
- 本地运行（已配置好）
- 本地打包jar（已配置好）
- 部署到Azure
    - 使用intellij IDE的Azure插件（已配置好）
    - 一键打包+部署
- 部署到gfit网站
    - 手动打包
    - 手动scp到远程服务器
    - 手动从docker中启动

## 需求列表

- 自动反馈
- 时间日志
- 结果显示
- 抽奖功能
- 答案配置 

 
 https://github.com/vlily/shareDemo/blob/master/mobile/prize/prize.html
 
 bug:
 3. 有些用户信息会丢失？
 4. 更新幻灯片图片
 5. p11 p12加载动画
 
 ## 杭州婚礼待办事项
 - 自动授权页面UI
 - [x]抽奖页面
 - [x]关注和取关会删除用户信息bug
 
 ## 路由情况
 - 关注
    - /
    - /oauth
- 问题
    - 问题列表 /qa.html
    - 抽奖页面 /question/1 (数字为题目id)
- 其他
    - hello
    - version
    - go