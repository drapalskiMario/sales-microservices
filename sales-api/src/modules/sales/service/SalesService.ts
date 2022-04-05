import { ConsumeMessage } from 'amqplib'
import { ProductQuantityDTO } from '../entities/Sales'
import { SalesRepository } from '../repository/SalesRepository'
import { SalesSender } from '../sender/SalesSender'

export class SalesService {
  constructor (
    private readonly salesRepository: SalesRepository,
    private readonly salesSender: SalesSender
  ) {}

  async createSales (products: ProductQuantityDTO[], userId: string) {
    const status = 'PENDING'
    const sales = await this.salesRepository.save({ products, userId, status })
    await this.salesSender.sender({
      salesId: sales._id,
      products: sales.products
    })
  }

  async findAll () {
    return await this.salesRepository.findAll()
  }

  async findById (id: string) {
    return await this.salesRepository.findById(id)
  }

  async updateSales (message: ConsumeMessage) {
    const { content }: any = message
    const { salesId, status } = JSON.parse(content.toString())
    await this.salesRepository.updateSalesStatus(salesId, status)
  }

  async findSalesByProductId (productId: string) {
    const sales = await this.salesRepository.findSalesByProduct(productId)
    return sales.map(({ _id }) => _id)
  }

  async validateStock (products: ProductQuantityDTO[], token: string) {
    return this.salesRepository.validateStock(products, token)
  }
}
