const localStorageKey = 'NebudPeckaFrontendApp'

const getLocalData = () =>
  JSON.parse(window.localStorage.getItem(localStorageKey))

const setLocalData = data =>
  window.localStorage.setItem(localStorageKey, JSON.stringify(data))

// public API:

export const clear = () => {
  window.localStorage.clear()
}

export const setToken = token => {
  setLocalData({ ...getLocalData(), token })
}

export const getToken = () => {
  const data = getLocalData()
  if (data) {
    return data.token
  }
}

export const setMyUser = myUser => {
  setLocalData({ ...getLocalData(), myUser })
}

export const getMyUser = () => {
  const data = getLocalData()
  if (data) {
    return data.myUser
  }
}
