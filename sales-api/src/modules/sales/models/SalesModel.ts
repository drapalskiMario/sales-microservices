import { Schema, model } from 'mongoose'
import { Sales } from '../entities/Sales'

const salesSchema = new Schema<Sales>({
  products: { type: [Object], required: true },
  userId: { type: String, required: true },
  status: { type: String, required: true }
}, { timestamps: true })

export const SalesModel = model<Sales>('Sales', salesSchema)
