import { Request, Response } from 'express'
import { HttpStatusResponse } from '../../../utils/enums/Status'
import { SalesService } from '../service/SalesService'
import { SalesValidator } from '../validator/SalesValidator'

export class SalesController {
  private invalidParamsMessage = 'Invalid parameters'

  constructor (
    private readonly salesService: SalesService,
    private readonly salesValidator: SalesValidator
  ) { }

  async createSales (request: Request, response: Response) {
    try {
      const { id }: any = request.user
      const { products } = request.body
      const { authorization } = request.headers
      const errorsInParams = this.salesValidator.createSalesValidator(id, products)
      if (!errorsInParams) {
        await this.salesService.validateStock(products, authorization!)
        await this.salesService.createSales(products, id)
        return response.status(HttpStatusResponse.noContent).send()
      } else {
        return response.status(HttpStatusResponse.badRequest).json({
          error: this.invalidParamsMessage
        })
      }
    } catch (error: any) {
      console.error('error to process createSales', error)
      return response.status(HttpStatusResponse.internalServerError).send()
    }
  }

  async findAllSales (request: Request, response: Response) {
    try {
      const salesList = await this.salesService.findAll()
      return response.status(HttpStatusResponse.success).json({ salesList })
    } catch (error: any) {
      console.error('error to process findAllSales', error)
      return response.status(HttpStatusResponse.internalServerError).send()
    }
  }

  async findSalesById (request: Request, response: Response) {
    try {
      const { id } = request.params
      if (id) {
        const sales = await this.salesService.findById(id)
        return response.status(HttpStatusResponse.success).json({ sales })
      } else {
        return response.status(HttpStatusResponse.badRequest).json({
          error: this.invalidParamsMessage
        })
      }
    } catch (error) {
      console.error('error to process findSalesById', error)
      return response.status(HttpStatusResponse.internalServerError).send()
    }
  }

  async findSalesByProductId (request: Request, response: Response) {
    try {
      const { id } = request.params
      if (id) {
        const salesIds = await this.salesService.findSalesByProductId(id)
        return response.status(HttpStatusResponse.success).json({ salesIds })
      } else {
        return response.status(HttpStatusResponse.badRequest).json({
          error: this.invalidParamsMessage
        })
      }
    } catch (error) {
      console.error('error to process findSalesById', error)
      return response.status(HttpStatusResponse.internalServerError).send()
    }
  }
}
