import { randomUUID } from 'crypto'
import { compareSync, hashSync } from 'bcrypt'
import { UserRepository } from '../repository/UsuarioRepository'
import { CustomException } from '../../../utils/errors/UserExecption'
import { Status } from '../../../utils/enums/Status'
import { Secret, sign } from 'jsonwebtoken'
import { UserValidator } from '../validator/UserValidor'

export class UserService {
  constructor (
    private readonly userRepository: UserRepository,
    private readonly userValidator: UserValidator,
    private readonly jwtSecret: Secret
  ) { }

  async create (name: string, email: string, password: string) {
    const invalidParams = this.userValidator.createUserValidator(name, email, password)
    if (!invalidParams) {
      const userExists = await this.userRepository.findByEmail(email)
      if (!userExists) {
        const id = randomUUID()
        const passwordHash = hashSync(password, 12)
        await this.userRepository.create(id, name, email, passwordHash)
      } else {
        throw new CustomException(Status.badRequest, 'user with email alredy exists')
      }
    } else {
      throw new CustomException(Status.badRequest, 'invalid params')
    }
  }

  async findOne (email: string) {
    return await this.userRepository.findByEmail(email)
  }

  async login (email: string, password: string) {
    const invalidParams = this.userValidator.loginUserValidator(email, password)
    if (!invalidParams) {
    const user = await this.userRepository.findByEmail(email)
    if (user) {
      const passwordCompare = compareSync(password, user.password)
      if (passwordCompare) {
        const payload = { id: user.id, name: user.name, email: email }
        const accessToken = sign(payload, this.jwtSecret, { expiresIn: '1d' })
        return accessToken
      }
    } else {
      throw new CustomException(Status.unauthorized)
    }
  } else {
    throw new CustomException(Status.badRequest, 'invalid params')
  }
  }
}
