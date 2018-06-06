#!/bin/bash

whoami

rootPath=$1
#warPath=$2
#contextPath=$3
applicationName=$2
#branch=$5
codePath=$3
isModule=$4
moduleName=$5

# 代码目录
workspace=$codePath'/'$applicationName

echo '************************'
echo 'rootPath='$rootPath
#echo 'warPath='$warPath
#echo 'contextPath='$contextPath
echo 'applicationName='$applicationName
#echo 'branch='$branch
echo 'workspace='$workspace
echo 'codePath='$codePath
echo 'isModule='$isModule
echo 'moduleName='$moduleName
echo
echo '************************'

cd $workspace

echo 'compiling'

# 执行mvn命令
if [ "$isModule" == "false" ]; then
  echo 'mvn -am -U clean package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true'
  echo
  mvn -am -U clean package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
else
  echo 'mvn -am -U clean package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -pl '$moduleName
  echo
  mvn -am -U clean package -Dmaven.test.skip=true -Dmaven.javadoc.skip=true pl $moduleName
fi

exit 0