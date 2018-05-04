nohup  /opt/big_data/spark-2.2.0-bin-hadoop2.7/bin/spark-submit \
  --class io.recommendation.stream.Main \
  --master spark://192.168.102.3:7077 \
  --deploy-mode client \
  --executor-cores 2 \
  --num-executors 2 \
  /root/stream.jar > ~/stream2.out &