#!/bin/bash

#define our environment variables
STACK_NAME=cfn-ecr-py-api
AWS_REGION=us-east-1
AWS_ACCOUNT_ID=636181284446 #[aws-acc-id]
IMAGE_NAME=buggy-app
TAG=latest
REPO_NAME=$IMAGE_NAME

#login to ECR using Docker credentials generated from AWS creds
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com

#build our image
docker build -t $IMAGE_NAME .

#tag & push our image to ECR
docker tag $IMAGE_NAME:$TAG $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$IMAGE_NAME:$TAG
docker push $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$IMAGE_NAME:$TAG

#checks if stack exists and either creates or updates it according to findings
if aws ecr describe-repositories --repository-names $REPO_NAME > /dev/null 2>$1; then
    aws ecr delete-repository --repository-name $REPO_NAME
else
    aws ecr create-repository --repository-name $REPO_NAME
fi