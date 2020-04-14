import React, { useEffect, useState } from 'react'
import { Button } from 'react-bootstrap'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { useFormikContext } from 'formik'

const delaySpinner = 300

export default ({ onClick, children, ...rest }) => {
  const { isSubmitting } = useFormikContext()
  const [showSpinner, setShowSpinner] = useState(true)

  useEffect(() => {
    const timer = setTimeout(() => {
      setShowSpinner(true)
    }, delaySpinner)
    return () => {
      clearTimeout(timer)
    }
  }, [showSpinner])

  const handleClick = (...args) => {
    setShowSpinner(false)
    onClick && onClick(...args)
  }

  return (
    <>
    <Button type="submit"
            disabled={isSubmitting}
            onClick={handleClick}
            {...rest}>
      {children}
    </Button>
    {isSubmitting && showSpinner &&
    <FontAwesomeIcon icon="spinner" fixedWidth size="lg" spin
                     className="ml-1"/>
    }
    </>
  )
}
