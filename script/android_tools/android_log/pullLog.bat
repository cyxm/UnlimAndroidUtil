adb logcat -v time -f /mnt/sdcard/AllLog.log
adb pull /mnt/sdcard/AllLog.log e:/android_log/AllLog.log
pause