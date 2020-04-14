import React from 'react'
import { useHistory } from 'react-router-dom'
import { Card, Col, Form, Row } from 'react-bootstrap'
import * as Yup from 'yup'
import { capitalCase } from 'change-case'
import { FieldGroup, FormikForm, SubmitButton } from '../form'
import { register } from '../api'
import { clear } from '../state/local-storage'

const mapServerErrorCodeToLabel = (field, code) => {
  if (field === 'email' && code === 'duplicate') {
    return 'Tato emailová adresa je již zaregistrována'
  }
  return code
}

const preFillName = (values, setFieldValue) => {
  const { email, name } = values
  if (email && !name) {
    const defaultName = capitalCase(email.substring(0, email.indexOf('@')))
    setFieldValue('name', defaultName)
  }
}

export default () => {
  const history = useHistory()

  const handleSubmit = async data => {
    clear()
    await register(data)
    history.push('/login')
  }

  return (
    <Card>
      <Card.Body>
        <Card.Title>
          Registrace
        </Card.Title>

        <FormikForm
          initialValues={{ email: '', password: '', name: '' }}
          validationSchema={
            Yup.object({
              email: Yup.string()
                .email()
                .required(),
              password: Yup.string()
                .min(8)
                .required(),
              name: Yup.string()
                .required()
            })
          }
          onSubmit={handleSubmit}
          mapServerErrorCodeToLabel={mapServerErrorCodeToLabel}
        >
          {({ values, setFieldValue }) => <>
          <FieldGroup as={Form.Control} name="email" label="Email" sm={[2, 9]} required
                      isValid={false} autoFocus autoComplete="username"/>
          <FieldGroup as={Form.Control} name="password" label="Heslo" sm={[2, 9]} required
                      type="password" autoComplete="current-password"/>
          <FieldGroup as={Form.Control} name="name" label="Jméno" sm={[2, 9]} required
                      onFocus={() => preFillName(values, setFieldValue)}/>

          <Form.Group as={Row}>
            <Col sm={{ offset: 2 }}>
              <SubmitButton>Zaregistrovat se</SubmitButton>
            </Col>
          </Form.Group>
          </>}
        </FormikForm>
      </Card.Body>
    </Card>
  )
}
