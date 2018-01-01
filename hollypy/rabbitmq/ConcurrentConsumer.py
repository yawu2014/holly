# coding=utf-8
import json
import pika
def add_points(msg):
    print ("message :"+str(msg))

if __name__ == "__main__":
    AMQP_SERVER = "localhost"
    AMQP_USER = "alert_user"
    AMQP_PASS = "alertme"
    AMQP_VHOST = "/"
    AMQP_EXCHANGE = "upload-pictures"
    creds_broker = pika.PlainCredentials(AMQP_USER,AMQP_PASS)
    conn_params = pika.ConnectionParameters(AMQP_SERVER,virtual_host=AMQP_VHOST,
                                            credentials = creds_broker)
    conn_broker = pika.BlockingConnection(conn_params)
    channel = conn_broker.channel()
    channel.exchange_declare(exchange=AMQP_EXCHANGE,type="fanout",auto_delete=False)

    channel.queue_declare(queue="add-points",auto_delete=False)
    channel.queue_bind(queue="critical",exchange=AMQP_EXCHANGE)
    channel.basic_consume(add_points,queue="critical",no_ack=False,consumer_tag="critical")
    channel.start_consuming()