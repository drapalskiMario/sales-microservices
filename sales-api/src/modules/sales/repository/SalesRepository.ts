import { ProductQuantityDTO, Sales } from '../entities/Sales'
import { Model } from 'mongoose'
import axios from 'axios'
import { PRODUCT_API_URL } from '../../../utils/constants'

export class SalesRepository {
  constructor (
    private readonly salesModel: Model<Sales>
  ) {}

  async save (sales: Sales) {
    return await this.salesModel.create(sales)
  }

  async findAll () {
    return await this.salesModel.find()
  }

  async findById (id: string) {
    return await this.salesModel.findById(id)
  }

  async updateSalesStatus (id: string, status: string) {
    const sales = await this.salesModel.findById(id)
    if (sales) {
      sales.status = status
      await this.salesModel.updateOne({ _id: id }, { status: status })
    }
  }

  async findSalesByProduct (productId: string) {
    return await this.salesModel.find({
      'products.productId': Number(productId)
    })
  }

  async validateStock (products: ProductQuantityDTO[], token: string) {
    try {
      const productsObj = { products }
      await axios.post(PRODUCT_API_URL!, productsObj, {
        headers: { authorization: token }
      })
    } catch (error: any) {
      throw new Error('Error response from Product-API.')
    }
  }
}
