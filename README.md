# data-process
数据处理
## 引言
近期参与公司的一项翻译工作，需要将项目中涉及的汉字翻译成对应的英文。由于手动翻译效率低下，所以开发一个数据处理程序。
## 版本功能预览
### 1.0.0
1.检索指定路径下符合指定正则表达式的关键字，将关键字记录至 **.csv文件中；  
2.检索指定路径下符合指定正则表达式的关键字，并将关键字与其对应的翻译记录至 **.csv文件中；  
3.根据指定的 **.csv文件中的内容替换指定路径下的原文件；  
4.检索指定路径下符合指定正则表达式的关键字，并将关键字直接替换成翻译结果；
## 使用指南
### 运行环境
1.JRE1.8及以上
### 运行
1.java -jar data-process-1.0.0.jar   
2.java -jar data-process-1.0.0.jar > log.file 2>&1 &     (后台运行)
### 停止
1.lsof -i|grep 8099
2.kill -9 (1.中pid号)
### 使用详解
1.默认端口8099，服务启动后，浏览器打开 http://localhost:8099/swagger-ui.html#/data-process-controller 查看接口。swagger使用指南请移步 https://swagger.io/ ；
2.浏览器打开 https://documenter.getpostman.com/view/3545239/collection/RVnTmLz4 查看接口示例报文；

