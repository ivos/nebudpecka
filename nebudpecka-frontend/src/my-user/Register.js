import React from 'react'
import { usePrevious } from 'react-use'
import { Formik } from 'formik'
import { Button, Col, Form, Row } from 'react-bootstrap'
import * as Yup from 'yup'
import { register } from '../api'
import { FieldGroup } from '../form'

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

      <Formik
        initialValues={{ email: '', password: '', name: '' }}
        initialStatus={{}}
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
        onSubmit={(values, { setSubmitting, setStatus }) => {
          setStatus({})
          register(values)
            .then(() => setSubmitting(false))
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
        {({ handleSubmit, touched, dirty, errors, status, isSubmitting, isValid }) => {
          // React.useEffect(() => {
          //   console.log('errors', errors, 'isSubmitting', isSubmitting)
          //   const errorFieldNames = Object.keys(errors)
          //
          //   if (isSubmitting && errorFieldNames.length > 0) {
          //     console.log('>>>> first field name', errorFieldNames[0])
          //   }
          // })

          const prevSubmitting = usePrevious(isSubmitting)
          React.useEffect(() => {
            const errorFieldNames = Object.keys(errors)
            const serverErrorFieldNames = Object.keys(status)
            console.log('==== Hook runs...', prevSubmitting, isSubmitting, isValid, errorFieldNames, serverErrorFieldNames)

            if ((prevSubmitting && !isSubmitting && !isValid && errorFieldNames.length > 0) ||
              (isSubmitting && serverErrorFieldNames.length > 0)) {
              console.log('==== Focusing')
              const selector = `[name="${[...errorFieldNames, ...serverErrorFieldNames][0]}"]`
              const errorElement = document.querySelector(selector)
              if (errorElement) {
                errorElement.focus()
                // const timeout =
                // setTimeout(() => errorElement.focus(), 1000)
                // return () => {
                //   console.log('clearing...')
                //   clearTimeout(timeout)
                // }
              }
            }
          }, [prevSubmitting, isSubmitting, isValid, status, errors])

          return (
            <Form noValidate onSubmit={handleSubmit}>

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
            </Form>
          )
        }}
      </Formik>
    </div>
  )
}
