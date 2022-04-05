import 'dotenv/config'
import express from 'express'
import { router } from './routes'
import { AUTH_PORT } from './utils/contants'

const app = express()
app.use(express.json())
app.use('/api/v1', router)

app.listen(Number(AUTH_PORT), async () => {
  console.info(`Server started successfully at ${AUTH_PORT}`)
})
