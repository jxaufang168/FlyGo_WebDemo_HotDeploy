# FlyGo_WebDemo_HotDeploy
文档地址：https://www.flygo520.com/docs/maven/maven-1am5uda82bu0v
[TOC]

# 热部署说明

1、一台待发布的服务器，下载安装好Tomcat环境。
2、通过Maven 热部署到远程服务器上。

# 服务器环境搭建

1、下载安装Tomcat： https://tomcat.apache.org/download-90.cgi
2、下载解压Tomcat，注意Tomcat依赖Java JDK环境。

![Tomcat目录](https://www.flygo520.com/uploads/maven/images/m_28221f61f7fe8d0bcf79a212f38e5b64_r.png#size=360x0)

3、启动Tomcat，启动在Tomcat 目录下的 `bin`，运行脚本 `startup.sh`启动。

>[info] `startup.sh` 启动Tomcat，`shutdown.sh` 关闭Tomcat

![查看tomcat启动情况](https://www.flygo520.com/uploads/maven/images/m_8afd0982c9d4e26a1eda79965f825a11_r.png#size=480x0)

# 配置Tomcat

1、浏览器访问 `http://(你的IP):8080` 出现 Tomcat 页面

![Tomcat界面](https://www.flygo520.com/uploads/maven/images/m_3a7a1de7caba9f9708001aca8c437ad4_r.png#size=360x0)

2、配置Tomcat访问权限，修改相关配置文件

没有配置权限的界面

![没有配置权限的界面](https://www.flygo520.com/uploads/maven/images/m_aeb215fb4fcf46ffbb991e0f3dbade86_r.png#size=360x0)

>[info] `manager-gui ` 浏览器界面管理权限
`manager-script` 脚本管理权限 【通过Maven 热部署需要的权限】

2.1、进入Tomcat 的 `conf` 目录 ，目录下有 `tomcat-users.xml`配置文件。

![tomcat-user配置文件](https://www.flygo520.com/uploads/maven/images/m_112b5095cc6f0df285db1e018b0ef29e_r.png#size=360x0)

2.2、编辑 `view tomcat-users.xml`, 添加如下配置，保存退出。

```bash
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<user username="tomcat" password="tomcat" roles="manager-gui,manager-script"/>
```

2.4、编辑 `webapps/manager/META-INF/context.xml`，把里面的访问限制注释。

```bash
<Context antiResourceLocking="false" privileged="true" >
<!-- 注释这里，去除对访问权限的设置
  <Valve className="org.apache.catalina.valves.RemoteAddrValve"
         allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1" />
  <Manager sessionAttributeValueClassNameFilter="java\.lang\.(?:Boolean|Integer|Long|Number|String)|org\.apache\.catalina\.filters\.CsrfPreventionFilter\$LruCache(?:\$1)?|java\.util\.(?:Linked)?HashMap"/>
-->
</Context>
```
>[danger]只修改`tomcat-users.xml`，Tomcat8以上可能还是无法访问。 问题参考： https://stackoverflow.com/questions/36703856/access-tomcat-manager-app-from-different-host?rq=1

2.4、重启Tomcat,重新打开 `http://(你服务器的IP):8080`,打开 `Manager App`。输入刚才配置的用户名和密码

![tomat 界面](https://www.flygo520.com/uploads/maven/images/m_d38b751ac92e437371a707352ebb78cd_r.png#size=360x0)

2.5、登录成功后，进入到后台管理界面。

![图形管理界面](https://www.flygo520.com/uploads/maven/images/m_830af7c83bf963a52757fb4fc346477f_r.png#size=360x0)

# 配置Maven web项目

1、配置 `pom.xml` 文件。

```bash
<build>
	<plugins>
		<plugin>
			<groupId>org.apache.tomcat.maven</groupId>
			<artifactId>tomcat7-maven-plugin</artifactId>
			<version>2.2</version>
			<configuration>
				<!-- 控制tomcat端口号 -->
				<port>8091</port>
				<!-- 项目发布到tomcat后的名称 -->
				<!-- / 相当于把项目发布名称为ROOT -->
				<!-- /ssm -->
				<path>/ssm</path>
				<!-- 热部署到服务器 -->
				<username>tomcat</username>
				<password>tomcat</password>
				<url>http://[你的服务器ip地址]:8080/manager/text</url>
			</configuration>
		</plugin>
	</plugins>
</build>
```

>[warning] 其中最关键的配置如下

```bash
<!-- 热部署到服务器 -->
<username>tomcat</username>
<password>tomcat</password>
<url>http://[你的服务器ip地址]:8080/manager/text</url>
```
2、右键项目 --> `run as` --> `maven build` -->输入

>[info] `tomcat7:deploy`   第一次发布
` tomcat7:redeploy`  不是第一次发布。重新发布。

![部署项目](https://www.flygo520.com/uploads/maven/images/m_e8fc1be2a5b17642ac62d9460712cfe6_r.png#size=360x0)

热部署项目

![输入命令部署](https://www.flygo520.com/uploads/maven/images/m_aaaafa71a2b2dd4a8daa4bf53fcfda0f_r.png#size=360x0)

3、IDE控制台开始上传部署项目

![热部署日志](https://www.flygo520.com/uploads/maven/images/m_133fac97232b141048a021e7f6f5cbb1_r.png#size=360x0)

4、查看服务器的Tomcat，`webapps` 目录下，已经上传部署好我们的项目

![服务器部署的项目](https://www.flygo520.com/uploads/maven/images/m_e54ab6736591718f17d2ef5fe2ce4545_r.png#size=360x0)

5、输入链接地址访问 `http://[你的服务器ip]:8080/ssm/getUsers`

![浏览器访问成功](https://www.flygo520.com/uploads/maven/images/m_8964943f74008fe3ea642f60b37846ce_r.png#size=360x0)

>[danger] 特别注意，服务器已经部署该项目，需要使用` tomcat7:redeploy`  重新发布。

# 演示Demo源码地址

https://github.com/jxaufang168/FlyGo_WebDemo_HotDeploy
