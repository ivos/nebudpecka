import React from 'react'
import { useFormikContext } from 'formik'
import { usePrevious } from 'react-use'

export default () => {
  const { errors, status, isValid, isSubmitting } = useFormikContext()
  const prevSubmitting = usePrevious(isSubmitting)

  React.useEffect(() => {
    const errorFieldNames = Object.keys(errors)
    const serverErrorFieldNames = Object.keys(status)
    // console.log('==== Hook runs...', { prevSubmitting, isSubmitting, isValid }, errorFieldNames, serverErrorFieldNames)

    if ((prevSubmitting && !isSubmitting && !isValid && errorFieldNames.length > 0) ||
      (isSubmitting && serverErrorFieldNames.length > 0)) {
      const selector = `[name="${[...errorFieldNames, ...serverErrorFieldNames][0]}"]`
      const errorElement = document.querySelector(selector)
      if (errorElement && errorElement.focus) {
        errorElement.focus()
      }
    }
  }, [prevSubmitting, isSubmitting, isValid, status, errors])

  return null
}
