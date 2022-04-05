export interface ProductQuantityDTO {
  productId: number
  quantity: number
}

export interface Sales {
  products: ProductQuantityDTO[]
  userId: string
  status?: string
}
