import { Request, Response } from 'express'
import { SalesController } from '../controller/SalesRestController'
import { SalesModel } from '../models/SalesModel'
import { SalesRepository } from '../repository/SalesRepository'
import { SalesSender } from '../sender/SalesSender'
import { SalesService } from '../service/SalesService'
import { SalesValidator } from '../validator/SalesValidator'

const salesControllerFactory = () => {
  const salesModel = SalesModel
  const salesRepository = new SalesRepository(salesModel)
  const salesSender = new SalesSender()
  const salesValidator = new SalesValidator()
  const salesService = new SalesService(salesRepository, salesSender)
  const salesController = new SalesController(salesService, salesValidator)
  return salesController
}

export const createSalesController = (request: Request, response: Response) => {
  const salesController = salesControllerFactory()
  return salesController.createSales(request, response)
}

export const findAllSalesController = (request: Request, response: Response) => {
  const salesController = salesControllerFactory()
  return salesController.findAllSales(request, response)
}
export const findSalesByIdController = (request: Request, response: Response) => {
  const salesController = salesControllerFactory()
  return salesController.findSalesById(request, response)
}
export const findSalesByProductIdController = (request: Request, response: Response) => {
  const salesController = salesControllerFactory()
  return salesController.findSalesByProductId(request, response)
}
