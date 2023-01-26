#!/usr/bin/env bash


BASE_PATH=/home/ubuntu/
BUILD_PATH=$(ls $BASE_PATH/app/*.jar)
JAR_NAME=$(basename $BUILD_PATH)
echo "> build 파일명: $JAR_NAME"


echo "> build 파일 복사"
DEPLOY_PATH=/home/ubuntu/app/temp/
cp $BUILD_PATH $DEPLOY_PATH

echo "> 현재 구동중인 Set 확인"
CURRENT_PROFILE=$(curl -s https://api.pulbatte.com/profile)
echo "> $CURRENT_PROFILE"

# 쉬고 있는 dev 찾기: dev이 사용중이면 dev2가 쉬고 있고, 반대면 dev이 쉬고 있음
if [ $CURRENT_PROFILE == dev1 ]
then
  IDLE_PROFILE=dev1
  NEW_IDLE_PROFILE=dev
  IDLE_PORT=8082
elif [ $CURRENT_PROFILE == dev ]
then
  IDLE_PROFILE=dev
  NEW_IDLE_PROFILE=dev1
  IDLE_PORT=8081
else
  echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
  echo "> dev 할당합니다. IDLE_PROFILE: dev"
  IDLE_PROFILE=dev
  NEW_IDLE_PROFILE=dev1
  IDLE_PORT=8081
fi

echo "> application.jar 교체"
NEW_IDLE_APPLICATION=$IDLE_PROFILE-demo.jar

IDLE_APPLICATION=$NEW_IDLE_PROFILE-demo.jar
IDLE_APPLICATION_PATH=$DEPLOY_PATH$IDLE_APPLICATION

# 미연결된 Jar로 신규 Jar 심볼릭 링크 (ln)
ln -Tfs $DEPLOY_PATH$JAR_NAME $IDLE_APPLICATION_PATH

echo "> $IDLE_PROFILE 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(pgrep -f $NEW_IDLE_APPLICATION)

if [ -z $IDLE_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -9 $IDLE_PID"
  kill -9 $IDLE_PID
  sleep 5
fi


echo "> $IDLE_PROFILE 배포"
nohup java -jar -Dspring.profiles.active=$NEW_IDLE_PROFILE $IDLE_APPLICATION_PATH > $DEPLOY_PATH/nohup.out 2>&1 &

# Nginx Port 스위칭을 위한 스크립트
echo "> 스위칭"
sleep 10

/home/ubuntu/app/switch.sh