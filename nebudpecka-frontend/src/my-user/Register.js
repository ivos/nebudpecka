import React from 'react'
import { ErrorMessage, Field, Formik } from 'formik'
import { Button, Col, Form, Row } from 'react-bootstrap'
import * as Yup from 'yup'
import { register } from '../api'

const mapServerErrorCodeToLabel = code => {
  if (code === 'duplicate') {
    return 'Tato emailová adresa je již zaresgistrována'
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

      <Formik
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
        onSubmit={(values, { setSubmitting, setErrors }) => {
          register(values)
            .catch(error => {
              const { errors: serverErrors } = error.response.data
              const errors = Object.fromEntries(
                serverErrors
                  .map(({ path, errorCode }) => ([path, errorCode]))
              )
              setErrors(mapServerErrorCodesToLabels(errors))
            })
        }}
      >
        {({ handleSubmit, touched, dirty, errors }) => (

          <Form noValidate onSubmit={handleSubmit}>

            <Form.Group as={Row} controlId="email">
              <Form.Label column sm={2}>
                Email
              </Form.Label>
              <Col sm={9}>
                <Field name="email" as={Form.Control} placeholder="Email"
                       isInvalid={touched.email && errors.email}
                       autoFocus
                />
                {/*isValid={touched.email && !errors.email}*/}
                <ErrorMessage name="email" component={Form.Control.Feedback} type="invalid"/>
              </Col>
            </Form.Group>

            <Form.Group as={Row} controlId="password">
              <Form.Label column sm={2}>
                Password
              </Form.Label>
              <Col sm={9}>
                <Field name="password" as={Form.Control} placeholder="Password"
                       isInvalid={touched.password && errors.password}
                       isValid={dirty && !errors.password}
                />
                <ErrorMessage name="password" component={Form.Control.Feedback} type="invalid"/>
              </Col>
            </Form.Group>

            <Form.Group as={Row} controlId="name">
              <Form.Label column sm={2}>
                Name
              </Form.Label>
              <Col sm={9}>
                <Field name="name" as={Form.Control} placeholder="Name"
                       isInvalid={touched.name && errors.name}
                       isValid={dirty && !errors.name}
                />
                <ErrorMessage name="name" component={Form.Control.Feedback} type="invalid"/>
              </Col>
            </Form.Group>

            <Form.Group as={Row}>
              <Col sm={{ offset: 2 }}>
                <Button type="submit">Zaregistrovat se</Button>
              </Col>
            </Form.Group>
          </Form>

        )}
      </Formik>
    </div>
  )
}
