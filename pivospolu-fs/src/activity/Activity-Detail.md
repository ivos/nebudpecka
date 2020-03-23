## Screen: Activity detail `/activities/detail`

### ReadOnlyForm: Aktivita

- Co: Pivo
- Kde: U Baby Jagy (6 km)
- Kdy: za 2 hodiny (_18:20 -- 21:00, 19. 3. 2020_)
- Název: V 7 na pivo
- Popis: Zarezervuju stůl na jméno Franta
- Kdo: Franta, Pepa, _Ferda_
- Status: Návrh
- [primary: <i class="fas fa-check"></i> Přijdu]() [: <i class="fas fa-times"></i> Nepřijdu]()

> NOT visible for the proposal owner.
> Adds / removes current user to the activity.

- [: Upravit](#/activities/edit)

> Only visible for the proposal owner.

- [primary: <i class="fas fa-check"></i> Potvrdit]() Dohodli jste se? Potvrďte aktivitu.

> Only visible for the proposal owner.
> Enabled in Status = Proposal.
> Sets Status = Confirmed.

- [: Zrušit]()

> Only visible for the proposal owner.
> Enabled in Status = Proposal, Confirmed.
> Sets Status = Canceled.

- [: Vrátit]() Vrátit aktivitu zpět do stavu Návrh.

> Only visible for the proposal owner.
> Enabled in Status = Confirmed, Canceled.
> Sets Status = Proposal    .

- [: Najít jinout](#/activities)
- [: Přehled](#/overview)

### Table: Diskuze

- Kdo
    - Pepa
    - Franta
    - Pepa
- Kdy
    - včera (_10:45, 18. 3. 2020_)
    - včera (_11:20, 18. 3. 2020_)
    - před 10 minutami (_16:30_)
- Text
    - Já bych mohl v 1845, budeš tam ještě?
    - Jasně
    - Tak nakonec dorazím už na začátek

### Form: Nová zpráva

- Text (R)
- [: <i class="fas fa-paper-plane"></i> Odeslat]()
