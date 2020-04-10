import React from 'react'
import { Button, Card, Col, Form, Row } from 'react-bootstrap'
import * as Yup from 'yup'
import { register } from '../api'
import { FieldGroup, FormikForm } from '../form'
import { capitalCase } from 'change-case'

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
          onSubmit={register}
          mapServerErrorCodeToLabel={mapServerErrorCodeToLabel}
        >
          {({ values, setFieldValue }) => <>
          <FieldGroup as={Form.Control} name="email" label="Email" sm={[2, 9]}
                      isValid={false} autoFocus autoComplete="username"/>
          <FieldGroup as={Form.Control} name="password" label="Heslo" sm={[2, 9]}
                      type="password" autoComplete="current-password"/>
          <FieldGroup as={Form.Control} name="name" label="Jméno" sm={[2, 9]}
                      onFocus={() => preFillName(values, setFieldValue)}/>

          <Form.Group as={Row}>
            <Col sm={{ offset: 2 }}>
              <Button type="submit">Zaregistrovat se</Button>
            </Col>
          </Form.Group>
          </>}
        </FormikForm>
      </Card.Body>
    </Card>
  )
}
