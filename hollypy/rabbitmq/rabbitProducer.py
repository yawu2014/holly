# coding=utf-8
import pika,sys
from pika import spec

''' run this script by
    python rabbitProducer.py msgBody
for example:
    python rabbitProducer.py "QQQQ"
'''
credentials = pika.PlainCredentials("guest", "guest")
conn_param = pika.ConnectionParameters(port=5672, credentials=credentials,host="localhost")

conn_broker = pika.BlockingConnection(conn_param)
channel = conn_broker.channel()
def confirm_handler(frame):
    if type(frame.method) == spec.Confirm.SelectOk:
        print "Channel in 'confirm' mode"
    elif type(frame.method) == spec.Basic.Nack:
        if frame.method.delivery_tag in msg_ids:
            print "Message lost!"
    elif type(frame.method) == spec.Basic.Ack:
        if frame.method.delivery_tag in msg_ids:
            print "Message received!"
            msg_ids.remove(frame.method.delivery_tag)

channel.confirm_delivery()
channel.exchange_declare(exchange="hello-exchange",type="direct",passive=False,durable=True
                         ,auto_delete=False)

msg = sys.argv[1]
msg_props = pika.BasicProperties()
msg_props.content_type = "text/plain"

msg_ids = []
channel.basic_publish(body=msg,exchange="hello-exchange",properties=msg_props,routing_key="hola")
msg_ids.append(len(msg_ids)+1)
channel.close()
