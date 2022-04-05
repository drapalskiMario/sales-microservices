import { ConsumeMessage, connect } from 'amqplib'
import { SalesService } from '../service/SalesService'

export class SalesListener {
  constructor (
    private readonly salesService: SalesService
  ) {}

  async listen () {
    try {
      const conn = await connect(process.env.RABBIT_MQ_URL!)
      const channel = await conn.createChannel()

      await channel.consume(
        process.env.SALES_CONFIRMATION_QUEUE!,
        async (message: ConsumeMessage | null) => {
          await this.salesService.updateSales(message as ConsumeMessage)
        },
        { noAck: true }
      )
    } catch (error) {
      console.error('error to process updateSales', error)
    }
  }
}
