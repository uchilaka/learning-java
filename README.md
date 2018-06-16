# Vacation Planner
An application to capture input from the user and help plan a vacation

## Pseudocode 

```js
/**
 * @class VacationPlanner
 * @description -
 * Application Design:
 *
 * :greeting
 * try {
 *  :captureName
 * } catch {
 *  :acknowledgeAndRetry*
 * }
 * :acknowledge
 *
 * try {
 *  :captureTravelDestination
 * } catch {
 *  :acknowledgeOrRetry
 * }
**/

function greeting(){
  // @TODO show greeting
}
function captureName() {
  try {
    // @TODO captureName
  } catch (e) {
    // @TODO acknowledge
    // @TODO retry
  }
}
function captureDestination() {
  try {
    // @TODO captureName
  } catch (e) {
    // @TODO acknowledge
    // @TODO retry
  }
}
function main() {
  greeting()
  captureName()
  captureDestination()
}

```