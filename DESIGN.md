# Design: an open directory and explicit forms

The existing coursework application manages students, courses and instructors. It is a working tool, not a portfolio landing page. Two generated internal references were inspected before implementation: a broad roster and split form sections. Their decorative texture/browser chrome and invented required fields were rejected. Generated concepts are not runtime assets or screenshots.

## Composition

The desktop directory uses a visible header, 1200 px content width, a concise functional title, one primary action, filters and open ruled rows. Mobile uses 20 px gutters and stacked rows with explicit course/belt labels. A native disclosure places filters after the heading without pushing the first complete row below the viewport: initial row top improved from 701 to 507 px at 360×800. JavaScript adjusts its open state on viewport changes without hiding a focused filter. Without JavaScript it stays open and functional.

Forms use a narrow explanatory column and a two-column field grid; at narrow widths both become single-column. Personal/enrollment/contact sections are separated by space and rules. Optional contact fields use a native disclosure that opens when their errors need attention. Record detail uses definition lists; deletion and error views use a single open message layout. There are no repeated metric cards, fake account controls, nested panels or inactive excursions/settings links.

Outfit is served locally with its full OFL notice. Paper #FAF8F3, ink #202421, muted #5E625C, copper #9B432F, selection #F2E3DB and boundary #85877E follow the reviewed shared palette. Computed shared contrasts are 14.81:1 ink/paper, 5.86:1 muted/paper, 6.49:1 white/copper and 3.43:1 control boundary/paper. Controls and actions have at least 48 px height; fields/buttons use 12 px corners. Links remain underlined and keyboard focus uses a visible 3 px outline. The app has no entry/scroll animation; CSS includes a reduced-motion guard.

## Behavior and language

Spanish is the default and English is the fallback resource bundle. Belts retain their original stored values and translate display labels. Seed course names and user-entered values remain data. Native date picker chrome follows the browser/OS language. Labels are explicit and separate from select options. Server validation preserves drafts and associates messages with fields; JavaScript focuses the error summary. Header language buttons submit the current editor draft for translation without writing. Without JavaScript, validation and translation still work.

Existing navigation paths remain. Input model fields are now English and entity binding is replaced by explicit mapping. Old programmatic form submissions need the new fields, form token and session. GET deletion is read-only confirmation; POST is explicit and checks eligibility. Error dispatch bypasses the form-token check so an invalid submission receives a recovery page rather than an empty second rejection. New IDs cannot overwrite records; missing references do not write. Transactions own relationship changes and deletion checks. The historical double monetary fields and lack of authentication/concurrent guarantees remain explicit limits.

## Data, storage and evidence

Six student/two course/two instructor seed records are original fictional fixtures; no real contact, medical note or external photo is required. The old commented seed and fake account UI were replaced. Existing historical media remains untouched but is not loaded by the new interface. An optional image URL is user input, not a bundled/licensed photo claim.

Default storage is memory-only H2 on loopback; file persistence is opt-in and seed insertion requires all three entity sets to be empty. The former DB file is not opened automatically. The documented demo-data directory is ignored by Git. Runtime checks cover both memory and an isolated restart with file storage. Cookie-only session tracking keeps session IDs out of links.

The 28 local tests, real browser paths, screenshots and packaged-JAR results are recorded in docs/verification.json. Screenshots are actual app pixels with fictional data. No generated concept is presented as executed UI. No new hosted CI, production deployment, physical device, screen reader certification or formal WCAG audit is claimed.
