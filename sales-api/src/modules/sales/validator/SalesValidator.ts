import Joi from 'joi'
import { ProductQuantityDTO } from '../entities/Sales'

export class SalesValidator {
  createSalesValidator (userId: string, products: Array<ProductQuantityDTO>) {
    const createSalesValidator = Joi.object({
      id: Joi.string().required(),
      products: Joi.array().items({
        productId: Joi.number().required(),
        quantity: Joi.number().required()
      })
    })
    const { error } = createSalesValidator.validate({ id: userId, products })
    return !!error
  }
}
