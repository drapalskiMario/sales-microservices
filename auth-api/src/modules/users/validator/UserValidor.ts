import Joi from 'joi'

export class UserValidator {
  createUserValidator (name: string, email: string, password: string) {
    const JoiValidator = Joi.object({
      name: Joi.string().required(),
      email: Joi.string().email().required(),
      password: Joi.string().required()
    })
    const createUserParams = { name, email, password }
    const { error } = JoiValidator.validate(createUserParams)
    return !!error
  }

  loginUserValidator (email: string, password: string) {
    const JoiValidator = Joi.object({
      email: Joi.string().email().required(),
      password: Joi.string().required()
    })
    const createUserParams = { email, password }
    const { error } = JoiValidator.validate(createUserParams)
    return !!error
  }
}
