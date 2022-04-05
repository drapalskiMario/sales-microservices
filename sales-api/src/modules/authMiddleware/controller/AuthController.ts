import { NextFunction, Request, Response } from 'express'
import { verify } from 'jsonwebtoken'
import { HttpStatusResponse } from '../../../utils/enums/Status'

export class AuthMiddlewareController {
  constructor (private readonly jwtSecret: string) {}

  async validateToken (request: Request, response: Response, next: NextFunction) {
    try {
      const { authorization } = request.headers
      if (authorization) {
        const token = authorization.replace('Bearer ', '')
        const decoded = verify(token, this.jwtSecret)
        const { id, email } = decoded as any
        request.user = { id, email }
        next()
      } else {
        return response.status(HttpStatusResponse.unauthorized).send()
      }
    } catch (error: any) {
      return response.status(HttpStatusResponse.internalServerError).json({ error: error.message })
    }
  }
}
