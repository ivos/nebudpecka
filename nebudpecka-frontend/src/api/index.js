import axios from 'axios'
import { getToken } from '../state/local-storage'

const getAuth = () => {
  const token = getToken()
  return token ? {
    username: getToken(),
    password: ''
  } : null
}

const requestInterceptor = config => ({
  ...config,
  auth: getAuth()
})

axios.interceptors.request.use(requestInterceptor)

// My user

export const register = data =>
  axios.post('/api/register', data)

export const login = data =>
  axios.post('/api/login', data)

const myUserBase = '/api/my-user'

export const getMyUser = () =>
  axios.get(myUserBase)
