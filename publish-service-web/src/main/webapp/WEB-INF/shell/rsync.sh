#!/usr/bin/expect

set warPath [lindex $argv 0]
set userName [lindex $argv 1]
set ip [lindex $argv 2]
set deployPath [lindex $argv 3]
set password [lindex $argv 4]

spawn rsync -avz --progress $warPath $userName@$ip:$deployPath
expect {
"*yes/no)? " {send "yes\n"; exp_continue}
"*password:" {send '$password\n'}
}
interact

exit 0
