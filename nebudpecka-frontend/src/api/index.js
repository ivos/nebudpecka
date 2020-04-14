import axios from 'axios'
import { getToken } from '../state/local-storage'

const delayRequests = null
// const delayRequests = 2000

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

// Delay each request:
if (delayRequests) {
  axios.interceptors.request.use(async (config) => {
    await new Promise(function (resolve) {
      setTimeout(resolve, delayRequests)
    })
    return requestInterceptor(config)
  })
}

// My user

export const register = data =>
  axios.post('/api/register', data)

export const login = data =>
  axios.post('/api/login', data)

const myUserBase = '/api/my-user'

export const getMyUser = () =>
  axios.get(myUserBase)
