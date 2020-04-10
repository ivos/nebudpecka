import React from 'react'
import { Formik } from 'formik'
import { Form } from 'react-bootstrap'
import FocusError from './FocusError'

const mapServerErrorCodesToLabels = (mapServerErrorCodeToLabel, serverErrors) => {
  return Object.fromEntries(
    serverErrors
      .map(({ path, errorCode }) => [path, mapServerErrorCodeToLabel(path, errorCode)])
  )
}

const wrapSubmit = (onSubmit, mapServerErrorCodeToLabel) => async (values, formikBag) => {
  formikBag.setStatus({})
  try {
    return await onSubmit(values, formikBag)
  } catch (error) {
    if (error.response.status === 422) {
      const { errors: serverErrors } = error.response.data
      const errors = mapServerErrorCodesToLabels(mapServerErrorCodeToLabel, serverErrors)
      formikBag.setStatus(errors)
    }
  }
}

export default ({ initialValues, onSubmit, mapServerErrorCodeToLabel, children, ...rest }) =>
  <Formik initialValues={initialValues}
          initialStatus={{}}
          onSubmit={wrapSubmit(onSubmit, mapServerErrorCodeToLabel)}
          {...rest}
  >
    {
      (props) => {
        const resolvedChildren = (typeof children === 'function') ? children(props) : children

        return (
          <Form noValidate onSubmit={props.handleSubmit}>
            {resolvedChildren}

            <FocusError/>
          </Form>
        )
      }
    }
  </Formik>
