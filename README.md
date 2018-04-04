# data-process
数据处理
## 引言
近期参与公司的一项翻译工作，需要将项目中涉及的汉字翻译成对应的英文。由于手动翻译效率低下，所以开发一个数据处理程序。其中翻译模块使用有道云API接口。
## 版本预览
### 1.0.0
1.检索指定路径下符合指定正则表达式的关键字，将关键字记录至 **.csv文件中；  
2.检索指定路径下符合指定正则表达式的关键字，并将关键字与其对应的翻译记录至 **.csv文件中；  
3.根据指定的 **.csv文件中的内容替换指定路径下的原文件；  
4.检索指定路径下符合指定正则表达式的关键字，并将关键字直接替换成翻译结果；
### 1.0.1
1.修复1.0.0版本中的bug；  
2.增加多文件字符类型支持；
## 技术预览
1.spring boot;  
2.java io;  
3.apache commons io;  
4.opencsv;  
5.apache httpcomponents;  
6.swagger2;  
7.juniversalchardet;  
## 使用指南
### 运行环境
1.JRE1.8及以上
### 运行
1.下载代码至本地，切换目录至 ../data-process/data-process/ ,执行 mvn package ;  
2.切换至 ../data-process/data-process/target/ ,使用如下两种方式运行：  
java -jar data-process-1.0.1.jar  
java -jar data-process-1.0.1.jar > log.file 2>&1 &     (后台运行)  
### 停止
1.lsof -i|grep 8099 ;  
2.kill -9 (1.中pid号) ;  
### 使用详解
1.默认端口8099，服务启动后，浏览器打开 http://localhost:8099/swagger-ui.html#/data-process-controller 查看接口。swagger使用指南请移步 https://swagger.io/ ；  
2.浏览器打开 https://documenter.getpostman.com/view/3545239/collection/RVnTmLz4 查看接口示例报文 ；  
3.详细图文教程请移步 doc/data-process.doc ；  
## 下一版本功能预期
1.单一大文件分割处理；  
2.多文件集合分批处理；  
3.异常中断重试机制；  

