## Entity: User

### Attributes:

- Id (APK bigint)
- Version (M bigint) - Optimistic lock
- Email (U string 100): `jan.novak@email.cz`
- Name (M string 100): Jan Novák
- Password hash (O string 100) - BCrypt
