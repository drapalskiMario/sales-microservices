import { Request, Response } from 'express'
import { Status } from '../../../utils/enums/Status'
import { UserService } from '../service/UsuarioService'

export class UserController {
  constructor (private readonly userService: UserService) {}

  async create (request: Request, response: Response) {
    try {
      const { name, email, password } = request.body
      await this.userService.create(name, email, password)
      return response.status(Status.created).send()
    } catch (error: any) {
      return response.status(error.status | Status.internalServerError).json({ error: error.message })
    }
  }

  async login (request: Request, response: Response) {
    try {
      const { email, password } = request.body
      const accessToken = await this.userService.login(email, password)
      return response.status(Status.success).json({ accessToken })
    } catch (error: any) {
      return response.status(error.status | Status.internalServerError).json({ error: error.message })
    }
  }
}
