import { DataTypes, Model } from 'sequelize'
import { sequelize } from '../../../utils/dbConfig'

export class User extends Model {
  declare id: string
  declare name: string
  declare email: string
  declare password: string
}

User.init({
  id: { type: DataTypes.STRING, primaryKey: true },
  name: { type: DataTypes.STRING, allowNull: false },
  email: { type: DataTypes.STRING, allowNull: false },
  password: { type: DataTypes.STRING, allowNull: false }
}, { sequelize, tableName: 'users' })

User.sync()
