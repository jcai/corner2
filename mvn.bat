@echo off
call build.bat -f   support/bootstrap/bootstrap.xml
java -classpath support\maven-lib\classworlds-1.1.jar -Dclassworlds.conf=support\maven-lib\m2.conf -Dmaven.home=support\maven-lib org.codehaus.classworlds.Launcher %*

