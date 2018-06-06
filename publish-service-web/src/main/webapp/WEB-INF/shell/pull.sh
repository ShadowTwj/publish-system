#!/bin/bash

whoami

rootPath=$1
gitName=$2
applicationName=$3
git=$4
branch=$5
codePath=$6
isTag=$7

echo '************************'
echo 'rootPath='$rootPath
echo 'gitName='$gitName
echo 'applicationName='$applicationName
echo 'git='$git
echo 'branch='$branch
echo 'codePath='$codePath
echo 'isTag='$isTag
echo
echo '************************'

if [ ! -d "$codePath" ]; then
  mkdir $codePath
fi

cd $codePath
pwd
echo
echo '************************'

# 如果目录不存在，执行git clone命令拉取代码
if [ ! -d "$applicationName" ]; then
  git clone $4
  mv $gitName $applicationName
  echo
  echo '************************'
fi

cd $applicationName
pwd
echo
echo '************************'

# master 分支不存在错误
echo git checkout master
result=`git checkout master 2>&1`
if [[ "$result" =~ "error" ]]; then
  echo "master分支不存在"
  exit -1
fi

# 获取最新分支、tag信息，不执行这一步可能出现拉取不到tag的问题
echo git pull
git pull


if [ "$isTag" == "false" ]; then
  echo "git checkout -B $branch"
  git checkout -B $branch origin/$branch
  git checkout $branch
  echo
  echo '************************'
  echo "git pull"
  git pull
  echo '************************'
else
  echo "git checkout $branch"
  git checkout $branch
  echo
  echo '************************'
fi

exit 0