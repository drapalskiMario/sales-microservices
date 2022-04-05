import { SalesListener } from '../listener/SalesListener'
import { SalesModel } from '../models/SalesModel'
import { SalesRepository } from '../repository/SalesRepository'
import { SalesSender } from '../sender/SalesSender'
import { SalesService } from '../service/SalesService'

export const startSalesListener = async () => {
  const salesModel = SalesModel
  const salesRepository = new SalesRepository(salesModel)
  const salesSender = new SalesSender()
  const salesService = new SalesService(salesRepository, salesSender)
  const salesListener = new SalesListener(salesService)
  await salesListener.listen()
}
