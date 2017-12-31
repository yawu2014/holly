# coding=utf-8
import json, pika

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
msg = json.dumps("xxxxx")
msg_props = pika.BasicProperties()
msg_props.content_type = "application/json"
msg_props.durable = False

channel.basic_publish(body=msg,exchange="alerts",properties=msg_props,routing_key=args.routing_key)