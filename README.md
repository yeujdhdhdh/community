<<<<<<< HEAD
## 码码更健康
=======
## 码码更健康1111
>>>>>>> a2ee2f9 (init repo)

## 资料
**PS D:\ideaworkspace\community> git init** 
Initialized empty Git repository in D:/ideaworkspace/community/.git/

**PS D:\ideaworkspace\community> git add .**
warning: in the working copy of '.mvn/wrapper/maven-wrapper.properties', LF will be replaced by CRLF the next time Git touches it
warning: in the working copy of 'mvnw', LF will be replaced by CRLF the next time Git touches it
warning: in the working copy of 'pom.xml', LF will be replaced by CRLF the next time Git touches it
warning: in the working copy of 'src/main/java/life/study/community/CommunityApplication.java', LF will be replaced by CRLF the next time Git touches it
warning: in the working copy of 'src/main/resources/application.properties', LF will be replaced by CRLF the next time Git touches it
warning: in the working copy of 'src/test/java/life/study/community/CommunityApplicationTests.java', LF will be replaced by CRLF the next time Git touches it

**PS D:\ideaworkspace\community> git commit -m "init repo"**
11 files changed, 637 insertions(+)
create mode 100644 .gitignore
create mode 100644 .mvn/wrapper/maven-wrapper.properties
create mode 100644 mvnw
create mode 100644 mvnw.cmd
create mode 100644 pom.xml
create mode 100644 src/main/java/life/study/community/CommunityApplication.java
create mode 100644 src/main/resources/templates/hello.html
create mode 100644 src/test/java/life/study/community/CommunityApplicationTests.java
On branch master
nothing to commit, working tree clean
commit 0d45cc8a52403aff98794437942d7585270da3cb (HEAD -> master)
Date:   Sun Jan 22 16:20:08 2023 +0800

    init repo
PS D:\ideaworkspace\community> git remote add origin https://github.com/yeujdhdhdh/community.git
PS D:\ideaworkspace\community> git status
On branch master
nothing to commit, working tree clean
PS D:\ideaworkspace\community> git push -u origin main
error: src refspec main does not match any
error: failed to push some refs to 'https://github.com/yeujdhdhdh/community.git'
PS D:\ideaworkspace\community> git push -u origin master
fatal: unable to access 'https://github.com/yeujdhdhdh/community.git/': SSL certificate problem: unable to get local issuer certificate
PS D:\ideaworkspace\community> git config --list
filter.lfs.clean=git-lfs clean -- %f
filter.lfs.required=true
http.sslbackend=openssl
http.sslcainfo=D:/Git/mingw64/ssl/certs/ca-bundle.crt
core.autocrlf=true
core.fscache=true
core.symlinks=false
pull.rebase=false
credential.helper=manager
credential.https://dev.azure.com.usehttppath=true
PS D:\ideaworkspace\community> git push -u origin master
fatal: unable to access 'https://github.com/yeujdhdhdh/community.git/': SSL certificate problem: unable to get local issuer certificate
PS D:\ideaworkspace\community> git config --global http.sslVerify false
PS D:\ideaworkspace\community> git push -u origin master               
warning: ----------------- SECURITY WARNING ----------------
warning: | TLS certificate verification has been disabled! |
warning: ---------------------------------------------------
warning: HTTPS connections may not be secure. See https://aka.ms/gcm/tlsverify for more information.
warning: ----------------- SECURITY WARNING ----------------
warning: | TLS certificate verification has been disabled! |
warning: ---------------------------------------------------
warning: HTTPS connections may not be secure. See https://aka.ms/gcm/tlsverify for more information.
info: please complete authentication in your browser...
warning: ----------------- SECURITY WARNING ----------------
warning: | TLS certificate verification has been disabled! |
warning: ---------------------------------------------------
warning: HTTPS connections may not be secure. See https://aka.ms/gcm/tlsverify for more information.
warning: | TLS certificate verification has been disabled! |
warning: ---------------------------------------------------
warning: HTTPS connections may not be secure. See https://aka.ms/gcm/tlsverify for more information.
Enumerating objects: 29, done.
Counting objects: 100% (29/29), done.
Delta compression using up to 12 threads
Compressing objects: 100% (18/18), done.
Writing objects: 100% (29/29), 59.40 KiB | 14.85 MiB/s, done.
Total 29 (delta 0), reused 0 (delta 0), pack-reused 0
To https://github.com/yeujdhdhdh/community.git
* [new branch]      master -> master
  branch 'master' set up to track 'origin/master'.
  PS D:\ideaworkspace\community> git config --global http.sslVerify false
<<<<<<< HEAD
=======
## 更新-------------------
>>>>>>> a2ee2f9 (init repo)
