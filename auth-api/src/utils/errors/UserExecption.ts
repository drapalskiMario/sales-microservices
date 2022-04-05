import { Status } from '../enums/Status'

export class CustomException extends Error {
  status: Status

  constructor (status?: Status, message?: string) {
    super(message)
    this.status = status || Status.internalServerError
    this.message = message || 'Internal server error'
    this.name = this.constructor.name
  }
}
