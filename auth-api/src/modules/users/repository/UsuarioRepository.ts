import { User } from '../model/User'

export class UserRepository {
  async create (id: string, name: string, email: string, password: string) {
    await User.create({ id, name, email, password })
  }

  async findByEmail (email: string) {
    return await User.findOne({ where: { email } })
  }
}
