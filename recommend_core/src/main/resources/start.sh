nohup  /opt/big_data/spark-2.2.0-bin-hadoop2.7/bin/spark-submit \
  --driver-class-path /opt/mysql-connector-java-5.1.38.jar \
  --class io.recommendation.engine.Main \
  --master spark://192.168.102.3:7077 \
  --deploy-mode client \
  /root/engine.jar >~/recommend.out &