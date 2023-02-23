
## 功能列表
开源论坛、问答系统，现有功能多社交平台登录(Github，Gitee)提问、回复、通知、最新问答、最热热大、消除零回复等功能。
## 技术栈
|  技术   |  链接   |
| --- | --- |
|  Spring Boot   |  http://projects.spring.io/spring-boot/#quick-start   |
|   MyBatis  |  https://mybatis.org/mybatis-3/zh/index.html   |
|   MyBatis Generator  |  http://mybatis.org/generator/   |
|   H2  |   http://www.h2database.com/html/main.html  |
|   Flyway  |   https://flywaydb.org/getstarted/firststeps/maven  |
|Lombok| https://www.projectlombok.org |
|Bootstrap|https://v3.bootcss.com/getting-started/|
|Github OAuth|https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/|
|UFile|https://github.com/ucloud/ufile-sdk-java|
|Bootstrap|https://v3.bootcss.com/getting-started/|
https://github.com/login/oauth/access_token?client_id=fd1d5ffc3e73e6451389&client_secret=9f03ff12c788b6b001bad9d2d55b1a1418d890d6&redirect_uri=http://localhost:8080/callback&state=1&code=11bf67c338c9a3550cce
## 扩展资料
[Spring 文档](https://spring.io/guides)    
[Spring Web](https://spring.io/guides/gs/serving-web-content/)   
[es](https://elasticsearch.cn/explore)    
[Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)    
[Bootstrap](https://v3.bootcss.com/getting-started/)    
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)    
[Spring](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)    
[菜鸟教程](https://www.runoob.com/mysql/mysql-insert-query.html)    
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)    
[Spring Dev Tool](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#using-boot-devtools)  
[Spring MVC](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)  
[Markdown 插件](http://editor.md.ipandao.com/)   
[UFfile SDK](https://github.com/ucloud/ufile-sdk-java)  
[Count(*) VS Count(1)](https://mp.weixin.qq.com/s/Rwpke4BHu7Fz7KOpE2d3Lw)  
[Git](https://git-scm.com/download)   
[Visual Paradigm](https://www.visual-paradigm.com)    
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)  
[Lombok](https://www.projectlombok.org)    
[ctotree](https://www.octotree.io/)   
[Table of content sidebar](https://chrome.google.com/webstore/detail/table-of-contents-sidebar/ohohkfheangmbedkgechjkmbepeikkej)    
[One Tab](https://chrome.google.com/webstore/detail/chphlpgkkbolifaimnlloiipkdnihall)    
[Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related)  
[Postman](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)

## 更新日志



mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

``sql:
@Insert("insert into user (name,account_id,token,gmt_create,GMT_MODIFIED) value (#{user.name},#{user.accountId},#{user.token},#{user.gmtCreate},#{user.gmtModified})")
void insert(@Param("user") User user);
@Select("select * from user where token=#{token}")
User findByToken(@Param("token") String token);
@Select("seect * from user where id=#{id}")
User findById(@Param("id")Integer id);
@Select("select * from user where account_id=#{accountId}")
User findByAccountId(@Param("accountId")String accountId);
@Update("update user set name=#{dbUser.name},token=#{dbUser.token},gmt_modified=#{dbUser.gmtModified},img_url=#{dbUser.imgUrl} where id=#{dbUser.id}")
void userUpdate(@Param("dbUser") User dbUser);
``
    