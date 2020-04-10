/* eslint no-template-curly-in-string: "off" */
import { setLocale } from 'yup'

setLocale({
  mixed: {
    required: 'Povinné'
  },
  string: {
    email: 'Musí být emailová adresa',
    min: 'Musí být alespoň ${min} znaků'
  }
})
