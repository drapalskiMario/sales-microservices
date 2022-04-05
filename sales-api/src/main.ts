import { info, error } from 'console'
import 'dotenv/config'
import express from 'express'
import { connect } from 'mongoose'
import { authMiddlewareFactory } from './modules/authMiddleware/factory/AuthFactory'
import { startSalesListener } from './modules/sales/factory/salesListenerFactory'
import { RabbitMqServer } from './rabbitmq-server'
import { router } from './routes'
import { MONGO_DB_URL, SALES_PORT } from './utils/constants'

const app = express()
app.use(express.json())
app.use(authMiddlewareFactory)
app.use('/api/v1', router)

;(async () => {
  try {
    await RabbitMqServer.createQueue()
    await startSalesListener()
    info('connected in rabbitmqserver')
    app.listen(SALES_PORT)
    info(`server started at port ${SALES_PORT}`)
    await connect(MONGO_DB_URL!)
    info('mongodb connect')
  } catch (err) {
    error('erro in connection', err)
  }
})()
