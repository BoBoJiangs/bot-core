#!/bin/bash
# 构建 bot-core 并同步 jar 到 qqbot 项目（防止源码与线上 jar 版本漂移）
# 用法: 在本仓库根目录执行 ./sync-to-qqbot.sh
set -e
cd "$(dirname "$0")"

echo "[1/3] 构建 bot-core ..."
mvn clean package -DskipTests -q -pl bot-core

echo "[2/3] 同步 jar 到 qqbot/lib ..."
cp bot-core/target/bot-core-1.0.0.jar /d/Java/qqbot/lib/bot-core-1.0.0.jar
ls -lh /d/Java/qqbot/lib/bot-core-1.0.0.jar

echo "[3/3] 完成。请到 qqbot 项目提交本次 jar 更新："
echo "  cd /d/Java/qqbot && git add lib/bot-core-1.0.0.jar && git commit -m 'chore: 同步 bot-core jar' && git push"
echo "  （业务项目也需要 mvn clean package 重新打包后部署）"
