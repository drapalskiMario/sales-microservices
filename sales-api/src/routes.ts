import { Router } from 'express'
import { createSalesController, findAllSalesController, findSalesByIdController, findSalesByProductIdController } from './modules/sales/factory/salesControllerFactory'

const router = Router()

router.post('/sales', createSalesController)
router.get('/sales', findAllSalesController)
router.get('/sales/:id', findSalesByIdController)
router.get('/sales/products/:id', findSalesByProductIdController)

export { router }
