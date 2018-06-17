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

function greetAndCaptureName(){
  // @TODO show greeting
  try {
    // @TODO captureName
  } catch (e) {
    // @TODO acknowledge
    // @TODO retry
  }
}
function inputDaysBudgetAndDestination() {
  try {
    // @TODO vacation days
    // @TODO capture dollar budget
    // @TODO capture destination alpha3
  } catch (e) {
    // @TODO acknowledge
    // @TODO retry
  }
}
function main() {
  greetAndCaptureName()
  inputDaysBudgetAndDestination()
  inputCurrencyExchangeRate()
  calcTime()
  calcAggregatedBudget()
  inputTimeDifference()
  calcTimezoneExamples()
  inputDestCountrySqAreaInKM2()
  calcDestCountrySqAreaInMI()
}

```