import { NextFunction, Request, Response } from 'express'
import { Status } from '../../../utils/enums/Status'
import { AuthMiddlewareService } from '../service/AuthMiddlewareService'

export class AuthMiddlewareController {
  constructor (private readonly authService: AuthMiddlewareService) {}

  async validateToken (request: Request, response: Response, next: NextFunction) {
    try {
      const { authorization } = request.headers
      if (authorization) {
        const token = authorization.replace('Bearer ', '')
        const user = await this.authService.validateToken(token)
        request.user = user
        next()
      } else {
        return response.status(Status.unauthorized).send()
      }
    } catch (error: any) {
      return response.status(error.status | Status.internalServerError).json({ error: error.message })
    }
  }
}
