import React from 'react'
import { NavLink } from 'react-router-dom'
import { Dropdown, Nav, Navbar, NavDropdown } from 'react-bootstrap'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { logout } from '../api'
import { clear, getMyUser } from '../state/local-storage'

export default ({ loggedId, loginStateChanged }) => {
  const handleLogout = async () => {
    await logout()
    clear()
    loginStateChanged()
  }

  return (
    <Navbar expand="sm">
      <Navbar.Brand href="#/">Nebuď pecka</Navbar.Brand>
      <Navbar.Toggle aria-controls="app-navbar-nav"/>
      <Navbar.Collapse id="app-navbar-nav">
        <Nav className="mr-auto">
          {loggedId && <Nav.Link href="#/">Home</Nav.Link>}
        </Nav>
        {!loggedId &&
        <Nav className="justify-content-end">
          <Nav.Link href="#/register">Registrace</Nav.Link>
          <Nav.Link href="#/login">Přihlášení</Nav.Link>
        </Nav>
        }
        {loggedId &&
        <Nav className="justify-content-end">
          <NavDropdown
            title={<FontAwesomeIcon icon="user" fixedWidth/>}
            alignRight={true}>
            <Dropdown.Header>
              <FontAwesomeIcon icon="user" fixedWidth className="mr-2"/>
              {getMyUser().name}
            </Dropdown.Header>
            <Dropdown.Divider/>

            <Dropdown.Item as={NavLink} to="/my-profile">
              <FontAwesomeIcon icon="user-cog" fixedWidth className="mr-2"/>
              Můj profil
            </Dropdown.Item>

            <Dropdown.Divider/>
            <Dropdown.Item as={NavLink} to="/login" onClick={handleLogout}>
              <FontAwesomeIcon icon="sign-out-alt" fixedWidth className="mr-2"/>
              Odhlásit se
            </Dropdown.Item>
          </NavDropdown>
        </Nav>
        }
      </Navbar.Collapse>
    </Navbar>
  )
}
