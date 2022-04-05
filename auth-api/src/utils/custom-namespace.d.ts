/* eslint-disable no-unused-vars */
declare namespace Express {
  interface Request {
    user?: { id, email }
  }
}
