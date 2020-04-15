import React, { useState } from 'react'
import { HashRouter as Router, Route, Switch } from 'react-router-dom'
import { getMyUser } from './state/local-storage'

import Header from './layout/Header'
import Register from './my-user/Register'
import Login from './my-user/Login'
import MyProfile from './my-user/MyProfile'

export default () => {
  const [loggedId, setLoggedId] = useState(!!getMyUser())

  const loginStateChanged = () => {
    setLoggedId(!!getMyUser())
  }

  return (
    <Router>
      <Header loggedId={loggedId} loginStateChanged={loginStateChanged}/>
      <Switch>
        <Route path="/register">
          <Register loginStateChanged={loginStateChanged}/>
        </Route>
        <Route path="/login">
          <Login loginStateChanged={loginStateChanged}/>
        </Route>
        <Route path="/my-profile">
          <MyProfile/>
        </Route>
        <Route path="/">
          <div>
            Home
          </div>
        </Route>
      </Switch>
    </Router>
  )
}
