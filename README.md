# ledger-gpt
GPT mirroring
## 简单说明
* 简单做了一个gpt的镜像页面，集成了gpt三种模型
## 技术栈
### 前端
vue、axios、element-ui
### 后端
springboot、mybatis-plus、mysql
### 其他
阿里oss文件上传
## 实现
* 通过向gpt提供的接口，向接口发送消息接收返回的数据，本项目实现了以下三种模型的接口
    1. https://api.openai.com/v1/completions(gpt3.0)
    2. https://api.openai.com/v1/chat/completions(gpt3.5)
    3. https://api.openai.com/v1/images/generations(文字转图像)

* 通过后端发送请求的方法(我是用java发送的请求，因为前端发送请求的authorization会被看到),将接口返回的数据让前端使用axios接收，渲染到页面上面

## 网站地址
http://ledgerhhh-ai.top