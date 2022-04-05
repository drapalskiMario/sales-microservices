import { NextFunction, Request, Response } from 'express'
import { JWT_SECRET } from '../../../utils/contants'
import { UserRepository } from '../../users/repository/UsuarioRepository'
import { UserService } from '../../users/service/UsuarioService'
import { UserValidator } from '../../users/validator/UserValidor'
import { AuthMiddlewareController } from '../controller/AuthController'
import { AuthMiddlewareService } from '../service/AuthMiddlewareService'

export const authMiddlewareFactory = (
  request: Request, response: Response, next: NextFunction) => {
    const userValidator = new UserValidator()
    const userRepository = new UserRepository()
    const userService = new UserService(userRepository, userValidator, JWT_SECRET!)
    const authMiddlewareService = new AuthMiddlewareService(userService, JWT_SECRET!)
    const authMiddlewareController = new AuthMiddlewareController(authMiddlewareService)

    return authMiddlewareController.validateToken(request, response, next)
}
