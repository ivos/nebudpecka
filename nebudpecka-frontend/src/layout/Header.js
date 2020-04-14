import React from 'react'
import { Link } from 'react-router-dom'

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
          <Link to="/my-profile">Můj profil</Link>
        </li>
      </ul>
    </nav>
  )
}
