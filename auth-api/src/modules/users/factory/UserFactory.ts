import { Request, Response } from 'express'
import { JWT_SECRET } from '../../../utils/contants'
import { UserController } from '../controller/UserController'
import { UserRepository } from '../repository/UsuarioRepository'
import { UserService } from '../service/UsuarioService'
import { UserValidator } from '../validator/UserValidor'

const userControllerFactory = () => {
  const userRepository = new UserRepository()
  const userValidator = new UserValidator()
  const userService = new UserService(userRepository, userValidator, JWT_SECRET!)
  const userController = new UserController(userService)

  return userController
}

export const createUserFactory = (request: Request, response: Response) => {
  const userController = userControllerFactory()
  return userController.create(request, response)
}

export const userLoginFactory = (request: Request, response: Response) => {
  const userController = userControllerFactory()
  return userController.login(request, response)
}
