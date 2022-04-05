import { Router } from 'express'
import { authMiddlewareFactory } from '../modules/authMiddleware/factory/AuthFactory'
import { createUserFactory, userLoginFactory } from '../modules/users/factory/UserFactory'

const router = Router()

router.post('/users', createUserFactory)
router.post('/users/login', userLoginFactory)

router.use(authMiddlewareFactory)

router.get('/hello', (req, res) => {
  return res.json({ message: 'Hello World' })
})

export { router }
