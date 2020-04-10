import React from 'react'
import { Button, Col, Form, Row } from 'react-bootstrap'
import * as Yup from 'yup'
import { register } from '../api'
import { FieldGroup, FormikForm } from '../form'

const mapServerErrorCodeToLabel = (field, code) => {
  if (field === 'email' && code === 'duplicate') {
    return 'Tato emailová adresa je již zaregistrována'
  }
  return code
}

export default () => {
  return (
    <div>
      Register

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
        <FieldGroup as={Form.Control} name="email" label="Email" sm={[2, 9]}
                    isValid={false} autoFocus/>
        {/*type="password"*/}
        <FieldGroup as={Form.Control} name="password" label="Password" sm={[2, 9]}/>
        <FieldGroup as={Form.Control} name="name" label="Name" sm={[2, 9]}/>

        <Form.Group as={Row}>
          <Col sm={{ offset: 2 }}>
            <Button type="submit">Zaregistrovat se</Button>
          </Col>
        </Form.Group>
      </FormikForm>
    </div>
  )
}
