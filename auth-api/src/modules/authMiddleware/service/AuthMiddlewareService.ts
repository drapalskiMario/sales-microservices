import { Status } from '../../../utils/enums/Status'
import { UserService } from '../../users/service/UsuarioService'
import { CustomException } from '../../../utils/errors/UserExecption'
import { verify } from 'jsonwebtoken'

export class AuthMiddlewareService {
  constructor (
    private readonly userService: UserService,
    private readonly jwtSecret: string
  ) { }

  async validateToken (token: string) {
    try {
      const decoded = verify(token, this.jwtSecret)
      if (decoded) {
        const { email } = decoded as any
        const userValid = await this.userService.findOne(email)
        if (userValid) {
          return { id: userValid.id, email: userValid.email }
        }
      }
    } catch (error) {
      throw new CustomException(Status.unauthorized, 'expired or invalidated token')
    }
  }
}
