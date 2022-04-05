import { connect, Channel, Connection } from 'amqplib'
import { PRODUCT_STOCK_UPDATE_QUEUE, PRODUCT_STOCK_UPDATE_ROUTING_KEY, PRODUCT_TOPIC, RABBIT_MQ_URL, SALES_CONFIRMATION_QUEUE, SALES_CONFIRMATION_ROUTING_KEY } from './utils/constants'

export class RabbitMqServer {
  private static conn: Connection
  private static channel: Channel

  static async createQueue () {
    try {
      this.conn = await connect(RABBIT_MQ_URL!)
      this.channel = await this.conn.createChannel()

      const queueAndRoutingKey = [
        {
          topic: PRODUCT_TOPIC!,
          queue: SALES_CONFIRMATION_QUEUE!,
          routingKey: SALES_CONFIRMATION_ROUTING_KEY!
        }, {
          topic: PRODUCT_TOPIC!,
          queue: PRODUCT_STOCK_UPDATE_QUEUE!,
          routingKey: PRODUCT_STOCK_UPDATE_ROUTING_KEY!
        }
      ]

      for await (const { queue, topic, routingKey } of queueAndRoutingKey) {
        this.channel.assertQueue(queue, { durable: true })
        this.channel.bindQueue(
          queue,
          topic,
          routingKey
        )
      }
    } catch (error) {
      console.error('error in connection rabbitmq server', error)
    }
  }
}
