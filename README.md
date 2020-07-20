# Ling （后端）

个人练习小项目，基于Spring Boot, Shiro, Vue, Element实现的一款前后端分离的后台权限管理系统

## 项目源码

 表头|后端源码|前端源码
  ---| :--:  | :--:
github|https://github.com/ling6930/Ling|https://github.com/ling6930/Ling-ui

## 使用教程

### 后端

1. 下载源码
   `git clone https://github.com/ling6930/Ling.git`
2. 编译代码
  找到 pom.xml，执行 `mvn clean install` 命令编译一键打包。
  一般来说不会有什么问题，如果还是编译不成功，可以按照优先级逐个编译试一试。
3. 导入数据库
   新建数据库，导入 doc/kitty.sql 脚本，导入初始化数据库。
   修改 application.yml 中的数据库连接和账号密码为自己的数据库配置。
4. 启动项目

### 前端

1. 下载源码
   `git clone https://github.com/ling6930/Ling-ui.git`
2. 安装node.js
3. 进入项目根目录，执行 `npm install`, 下载和安装项目相关依赖包
4. 执行 `npm run dev` 命令，启动项目，通过 http://localhost:8080 访问。

## 功能列表

- 系统登录：系统用户登录，系统登录认证（token方式）
- 用户管理：新建用户，修改用户，删除用户，查询用户
- 机构管理：新建机构，修改机构，删除机构，查询机构
- 角色管理：新建角色，修改角色，删除角色，查询角色
- 菜单管理：新建菜单，修改菜单，删除菜单，查询菜单
- 字典管理：新建字典，修改字典，删除字典，查询字典
- 系统日志：记录用户操作日志，查看系统执行日志记录

## 技术选型

### 后端
- Spring Boot 
- Mybatis-Generator
- MySQL 
- Shiro
- Druid
- Log4j

### 前端
- Vue,Vuex,Vue Router
- Axios
- ElementUI

## 系统展示

### 登陆页面
![Image text](https://github.com/ling6930/Ling-ui/blob/master/img-storage/%E7%99%BB%E9%99%86%E9%A1%B5%E9%9D%A2.PNG)

### 用户管理
![Image text](https://github.com/ling6930/Ling-ui/blob/master/img-storage/%E7%94%A8%E6%88%B7%E7%AE%A1%E7%90%86.PNG)

### 机构管理
![Image text](https://github.com/ling6930/Ling-ui/blob/master/img-storage/%E6%9C%BA%E6%9E%84%E7%AE%A1%E7%90%86.PNG)

### 角色管理
![Image text](https://github.com/ling6930/Ling-ui/blob/master/img-storage/%E8%A7%92%E8%89%B2%E7%AE%A1%E7%90%86.PNG)

### 菜单管理
![Image text](https://github.com/ling6930/Ling-ui/blob/master/img-storage/%E8%8F%9C%E5%8D%95%E7%AE%A1%E7%90%86.PNG)


### 字典管理
![Image text](https://github.com/ling6930/Ling-ui/blob/master/img-storage/%E5%AD%97%E5%85%B8%E7%AE%A1%E7%90%86.PNG)


### 系统日志
![Image text](https://github.com/ling6930/Ling-ui/blob/master/img-storage/%E7%B3%BB%E7%BB%9F%E6%97%A5%E5%BF%97.PNG)

## 鸣谢

- 项目主要参考 https://gitee.com/liuge1988/kitty, 感谢并推荐作者的教程 https://www.cnblogs.com/xifengxiaoma/p/9533018.html


