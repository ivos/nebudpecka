import React from 'react'
import { Button, Card, Col, Form, Row } from 'react-bootstrap'
import * as Yup from 'yup'
import { FieldGroup, FormikForm } from '../form'
import { getMyUser, login } from '../api'
import { clear, setMyUser, setToken } from '../state/local-storage'

const loadToken = async data => {
  const response = await login(data)
  const token = response.headers.location
  setToken(token)
}

const loadMyUser = async () => {
  const response = await getMyUser()
  const myUser = response.data
  setMyUser(myUser)
}

const handleSubmit = async data => {
  clear()
  await loadToken(data)
  await loadMyUser()
}

export default () => {
  return (
    <Card>
      <Card.Body>
        <Card.Title>
          Přihlášení
        </Card.Title>

        <FormikForm
          initialValues={{ email: '', password: '' }}
          validationSchema={
            Yup.object({
              email: Yup.string()
                .email()
                .required(),
              password: Yup.string()
                .required()
            })
          }
          onSubmit={handleSubmit}
        >
          <FieldGroup as={Form.Control} name="email" label="Email" sm={[2, 9]}
                      isValid={false} autoFocus autoComplete="username"/>
          <FieldGroup as={Form.Control} name="password" label="Heslo" sm={[2, 9]}
                      isValid={false} type="password" autoComplete="current-password"/>

          <Form.Group as={Row}>
            <Col sm={{ offset: 2 }}>
              <Button type="submit">Přihlásit se</Button>
            </Col>
          </Form.Group>
        </FormikForm>
      </Card.Body>
    </Card>
  )
}
