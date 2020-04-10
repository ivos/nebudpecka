import React from 'react'
import { Button, Col, Form, Row } from 'react-bootstrap'
import * as Yup from 'yup'
import { register } from '../api'
import { FieldGroup, FormikForm } from '../form'

const mapServerErrorCodeToLabel = code => {
  if (code === 'duplicate') {
    return 'Tato emailová adresa je již zaregistrována'
  }
  return code
}

const mapServerErrorCodesToLabels = errors => {
  return Object.fromEntries(Object.entries(errors)
    .map(([field, code]) => ([field, mapServerErrorCodeToLabel(code)]))
  )
}

export default () => {
  return (
    <div>
      Register

      <FormikForm
        initialValues={{ email: '', password: '', name: '' }}
        validationSchema={Yup.object({
          email: Yup.string()
            .email("Musí být emailová adresa")
            .required('Povinné'),
          password: Yup.string()
            .min(8, "Musí být alespoň 8 znaků")
            .required('Povinné'),
          name: Yup.string()
            .required('Povinné')
        })}
        onSubmit={(values, { setStatus }) => {
          setStatus({})
          return register(values)
            .catch(error => {
              // console.log('error response', error.response)
              if (error.response.status === 422) {
                const { errors: serverErrors } = error.response.data
                const errors = Object.fromEntries(
                  serverErrors
                    .map(({ path, errorCode }) => ([path, errorCode]))
                )
                setStatus(mapServerErrorCodesToLabels(errors))
              }
            })
        }}
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
