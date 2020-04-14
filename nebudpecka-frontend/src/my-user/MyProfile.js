import React from 'react'
import { Card, Form } from 'react-bootstrap'
import { StaticGroup } from '../form'
import { getMyUser } from '../state/local-storage'

export default () => {
  const myUser = getMyUser()

  return (
    <Card>
      <Card.Body>
        <Card.Title>
          Můj profil
        </Card.Title>

        <Form>
          <StaticGroup label="Jméno" sm={[2, 10]} value={myUser.name}/>
          <StaticGroup label="Email" sm={[2, 10]}>
            <code>{myUser.email}</code>
          </StaticGroup>
        </Form>
      </Card.Body>
    </Card>
  )
}
