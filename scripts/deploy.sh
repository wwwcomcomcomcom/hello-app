#!/bin/bash

IMAGE_NAME=hello-app-img
CONTAINER_NAME=hello-app
DOCKERFILE_NAME=Dockerfile

CURRENT_CONTAINER_ID=$(sudo docker ps -a -q -f name=$CONTAINER_NAME)

if [ ! -z "$CURRENT_CONTAINER_ID" ]
then
  echo "현재 구동 중인 컨테이너가 있습니다. 중지하고 삭제합니다."
  sudo docker stop $CONTAINER_NAME || true
  sudo docker rm $CONTAINER_NAME
fi

cd /home/ubuntu || exit
sudo docker build -t $IMAGE_NAME -f $DOCKERFILE_NAME .

sudo docker run -d --name $CONTAINER_NAME -p 8080:8080 --restart always $IMAGE_NAME