import React from 'react'
import { ErrorMessage, Field, Form as FormiForm, Formik } from 'formik'
import { Button, Col, Form, Row } from 'react-bootstrap'

export default () => {
  return (
    <div>
      Register

      <Formik
        initialValues={{ email: '' }}
        onSubmit={(values, { setSubmitting }) => {
          setTimeout(() => {
            alert(JSON.stringify(values, null, 2));
            setSubmitting(false);
          }, 400);
        }}
      >
        <FormiForm>

          <Form.Group as={Row} controlId="formHorizontalEmail">
            <Form.Label column sm={2}>
              Email
            </Form.Label>
            <Col sm={6}>
              <Field name="email" as={Form.Control} placeholder="Email"/>
            </Col>
            <Col sm={4}>
              <ErrorMessage name="email" component="div"/>
            </Col>
          </Form.Group>


          {/*<Field name="password" type="password"/>*/}

          {/*<Field name="name"/>*/}

          <Form.Group as={Row}>
            <Col sm={{ offset: 2 }}>
              <Button type="submit">Zaregistrovat se</Button>
            </Col>
          </Form.Group>
        </FormiForm>
      </Formik>
    </div>
  )
}
