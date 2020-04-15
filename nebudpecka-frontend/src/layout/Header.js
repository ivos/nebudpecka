import React from 'react'
import { Link } from 'react-router-dom'
import { logout } from '../api'
import { clear } from '../state/local-storage'

const handleLogout = async () => {
  await logout()
  clear()
}

export default () => {
  return (
    <nav>
      <ul>
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/register">Registrace</Link>
        </li>
        <li>
          <Link to="/login">Přihlášení</Link>
        </li>
        <li>
          <Link to="/login" onClick={handleLogout}>Odhlásit se</Link>
        </li>
        <li>
          <Link to="/my-profile">Můj profil</Link>
        </li>
      </ul>
    </nav>
  )
}
