import 'dotenv/config'
import { Sequelize } from 'sequelize'
import { AUTH_POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER } from './contants'

const sequelize = new Sequelize({
  database: AUTH_POSTGRES_DB,
  username: POSTGRES_USER,
  password: POSTGRES_PASSWORD,
  dialect: 'postgres',
  host: 'localhost',
  quoteIdentifiers: false,
  define: {
    timestamps: false,
    underscored: true,
    freezeTableName: true
  }
})

sequelize
  .authenticate()
  .then(() => {
    console.info('Connection has been stablished!')
  })
  .catch((error) => {
    console.error('Unable to connect to the database')
    console.error('error msg: ', error)
  })

export { sequelize }
