import { NextFunction, Request, Response } from 'express'
import { JWT_SECRET } from '../../../utils/constants'
import { AuthMiddlewareController } from '../controller/AuthController'

export const authMiddlewareFactory = (
  request: Request, response: Response, next: NextFunction) => {
    const authMiddlewareController = new AuthMiddlewareController(JWT_SECRET!)
    return authMiddlewareController.validateToken(request, response, next)
}
