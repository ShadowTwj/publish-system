#!/bin/bash

whoami

rootPath=$1
contextPath=$2
applicationName=$3
gitName=$4
module=$5
isModel=$6
codePath=$7
userName=$8
ip=$9
deployUrl=${10}
password=${11}

#判断是否为模块
if [ "$isModel" == "false" ]; then
  warPath=$codePath'/'$applicationName'/target/'$gitName'.war'
else
  warPath=$codePath'/'$applicationName'/'$module'/target/'$module'.war'
fi

deployPath=$deployUrl'/'$contextPath'.war'

echo '************************'
echo
echo 'rootPath='$rootPath
echo 'contextPath='$contextPath
echo 'applicationName='$applicationName
echo 'module='$module
echo 'isModel='$isModel
echo 'codePath='$codePath
echo 'userName='$userName
echo 'ip='$ip
echo 'deployUrl='$deployUrl
echo 'warPath='$warPath
echo 'deployPath='$deployPath
echo
echo '************************'

echo 'deploying'
echo
echo 'rsync'
#echo 'spawn rsync -avz --progress '$warPath' '$userName'@'$ip':'$deployPath
echo
echo

$rootPath'/shell/rsync.sh' $warPath $userName $ip $deployPath $password

exit 0
