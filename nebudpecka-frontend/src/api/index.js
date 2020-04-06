import axios from 'axios'

// My user

export const register = data =>
  axios.post('/api/register', data)
