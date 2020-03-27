## Entity: Activity

### Attributes:

- Id (APK bigint)
- Version (M bigint) - Optimistic lock
- Owner (n:1 `#User`)
- Status (M enum) - Proposal, Confirmed, Canceled
- Type (n:1 `#ActivityType`)
- Location name (M string 200)
- Latitude (M decimal 2 + 15): 50.09652625485888 - 0 .. 90
- Longitude (M decimal 3 + 15): 14.411293328124998 - -180 .. +180
- Start time (M datetime) - Precision: minute
- End time (M datetime) - Precision: minute
- Name (M string 100)
- Description (O string)
