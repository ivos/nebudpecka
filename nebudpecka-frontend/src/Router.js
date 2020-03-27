import React from 'react'
import { HashRouter as Router, Route, Switch } from 'react-router-dom'

import Header from './layout/Header'
import Register from './my-user/Register'
import Login from './my-user/Login'

export default () => {
  return (
    <Router>
      <Header/>
      <Switch>
        <Route path="/register">
          <Register/>
        </Route>
        <Route path="/login">
          <Login/>
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
