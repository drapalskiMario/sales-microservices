import { connect } from 'amqplib'

export class SalesSender {
  async sender (message: any) {
    try {
      const conn = await connect(process.env.RABBIT_MQ_URL!)
      const channel = await conn.createChannel()
      return channel.publish(
        process.env.PRODUCT_TOPIC!,
        process.env.PRODUCT_STOCK_UPDATE_ROUTING_KEY!,
        Buffer.from(JSON.stringify(message))
      )
    } catch (error) {
      console.error('error to process createSales', error)
    }
  }
}
